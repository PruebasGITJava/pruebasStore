package com.store.tropifaunia.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.mail.service.impl.MailServiceImpl;
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

	@GetMapping(ConstantController.RAIZ)
	public String redirectToLogin() {
		ConstantController.LOG.info("Lanzando metodo: redirectToLogin()");
		ConstantController.LOG.info("Returning a la vista: login");
		return ConstantController.REDIRECT;

	}

	@GetMapping(ConstantController.LOGIN)
	public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ConstantController.LOG
				.info("Lanzando metodo: showLoginForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		ConstantController.LOG.info("Returning a la vista: login");
		return ConstantView.CONTACT_LOGIN;
	}

	@PostMapping(ConstantController.LOGIN_CHECK)
	public String login(@ModelAttribute(name = "userCredentials") Contact contact) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().isEmpty()) {

			for (Contact user : contactServiceImpl.findByAll()) {
				if (contact.getEmail().equals(user.getEmail())
						&& DigestUtils.md5Hex(contact.getPasswd()).equals(user.getPasswd())
						&& user.getActivation() == 1) {
					ConstantController.LOG.info("Returning a la vista: contacts");
					return ConstantView.CONTACTS;
				}
			}
		}
		ConstantController.LOG.info("Returning a la vista: login?error");
		return ConstantController.REDIRECT_ERROR;
	}

	@GetMapping(ConstantController.ADDCONTACT)
	public String showAddContactForm() {
		ConstantController.LOG.info("Lanzando metodo: showLoginForm() ");
		return ConstantController.ADDCONTACT;
	}

	@PostMapping(ConstantController.ADDCONTACT)
	public String addContact(@RequestBody Contact contact) throws MessagingException, IOException, TemplateException {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByAll();
			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail())) {
					ConstantController.LOG.info("Returning a la vista: login?error");
					return ConstantController.REDIRECT_ERROR;
				}
			}
			contact.setActivation(0);
			ConstantController.LOG.info("Activation mode off");
			String encriptMD5 = DigestUtils.md5Hex(contact.getPasswd());
			contact.setPasswd(encriptMD5);
			ConstantController.LOG.info("Encript MD5");
			contactServiceImpl.addContact(contact);
			ConstantController.LOG.info("Rest method: addContact()");
			mailServiceImpl.sendSimpleMessageHTMLP(contact.getEmail(), contact.getId());
			return ResponseEntity.ok(HttpStatus.OK + " Se dio de alta el usuario con email: '" + contact.getEmail()
					+ "', en breve recibirá un correo para la confirmación de la cuenta.").toString();
		}

		return ConstantController.REDIRECT_ERROR;
	}
}
