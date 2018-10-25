package com.store.tropifaunia.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.entity.Animals;
import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.mail.service.impl.MailServiceImpl;
import com.store.tropifaunia.services.GenerPasswdService;
import com.store.tropifaunia.services.impl.AnimalServiceImpl;
import com.store.tropifaunia.services.impl.ContactServiceImpl;

import freemarker.template.TemplateException;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactServiceImpl contactServiceImpl;
	@Autowired
	@Qualifier("mailServiceImpl")
	private MailServiceImpl mailServiceImpl;
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;

	@GetMapping(ConstantController.RAIZ)
	public String redirectToLogin() {
		ConstantController.LOG.info("Lanzando metodo: redirectToLogin()");
		ConstantController.LOG.info("Returning a la vista: login");
		return ConstantController.REDIRECT;

	}

	@GetMapping(ConstantController.LOGIN)
	public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout, HttpServletRequest request) {

		ConstantController.LOG
				.info("Lanzando metodo: showLoginForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		request.getSession().setAttribute("LOGGED-DATA", null);
		ConstantController.LOG.info("Returning a la vista: login");
		return ConstantView.CONTACT_LOGIN;
	}

	@PostMapping(ConstantController.LOGIN_CHECK)
	public String login(Model model, @ModelAttribute(name = "userCredentials") Contact contact,
			HttpServletRequest request) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().isEmpty()) {

			for (Contact user : contactServiceImpl.findByAll()) {
				if (contact.getEmail().equals(user.getEmail())
						&& DigestUtils.md5Hex(contact.getPasswd()).equals(user.getPasswd())
						&& user.getActivation() == 1) {
					request.getSession().setAttribute("LOGGED-DATA", contact);
					ConstantController.LOG.info("Returning a la vista: contacts");

					model.addAttribute("animals", animalServiceImpl.findByAll());
					double precio = 0;
					int cantidad = 0;

					for (Animals animal : animalServiceImpl.findByAll()) {
						precio = precio + animal.getEuros();
						cantidad = cantidad + animal.getNumero();
					}
					model.addAttribute("precio", precio);
					model.addAttribute("cantidad", cantidad);
					return ConstantView.CONTACTS;
				}
			}
		}
		ConstantController.LOG.info("Returning a la vista: login?error");
		return ConstantController.REDIRECT_ERROR;
	}

	@GetMapping(ConstantController.ADDCONTACT)
	public String showAddContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ConstantController.LOG
				.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		ConstantController.LOG.info("Returning a la vista: addcontact");
		return ConstantView.ADDCONTACT;
	}

	@PostMapping(ConstantController.ADDCONTACT_CHECK)
	public String addContact(@ModelAttribute(name = "userCredentials") Contact contact)
			throws MessagingException, IOException, TemplateException {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByAll();
			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail())) {
					ConstantController.LOG.info("PARAMETROS: email=" + contact.getEmail() + ", email-introducido: "
							+ user.getEmail() + "'.");
					return ConstantController.REDIRECT_ERROR;
				}
			}
			contact.setActivation(0);
			ConstantController.LOG.info("Rest method: setActivation(0)");
			String encriptMD5 = DigestUtils.md5Hex(contact.getPasswd());
			contact.setPasswd(encriptMD5);
			ConstantController.LOG.info("Rest method: md5Hex(paswd)");
			contactServiceImpl.addContact(contact);
			ConstantController.LOG.info("Rest method: addContact()");
			mailServiceImpl.sendSimpleMessageHTMLP(contact.getEmail(), contact.getId());
			return ConstantView.ADDCONTACT_CHECK;
		}

		return ConstantController.REDIRECT_ERROR;
	}

	@GetMapping(ConstantController.RESET)
	public String showResetPasswd(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ConstantController.LOG
				.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		ConstantController.LOG.info("Returning a la vista: addcontact");
		return ConstantView.RESET;
	}

	@PostMapping(ConstantController.RESET_CHECK)
	public String sendMail(@ModelAttribute(name = "userCredentials") Contact contact) {

		String passwd = GenerPasswdService.getPassword(
				GenerPasswdService.MINUSCULAS + GenerPasswdService.MAYUSCULAS + GenerPasswdService.ESPECIALES, 10);

		if (!contact.getEmail().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByAll();

			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail())) {
					mailServiceImpl.sendSimpleMessagePasswdReset(contact, passwd);
					String encriptMD5 = DigestUtils.md5Hex(passwd);
					contactServiceImpl.updatePasswd(user, encriptMD5);

				}
			}
		}
		return ConstantView.RESET_CHECK;
	}
}
