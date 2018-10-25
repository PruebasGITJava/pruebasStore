package com.store.tropifaunia.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * The Class LoginController.
 */
@Controller
public class LoginController {

	/** The contact service impl. */
	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactServiceImpl contactServiceImpl;
	/** The mail service impl. */
	@Autowired
	@Qualifier("mailServiceImpl")
	private MailServiceImpl mailServiceImpl;
	/** The animal service impl. */
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;
	/** The Constant LOG. */
	public static final Log LOG = LogFactory.getLog(LoginController.class);

	/**
	 * Redirect to login.
	 *
	 * @return the string
	 */
	@GetMapping(ConstantController.RAIZ_LOGIN)
	public String redirectToLogin() {
		LOG.info("Lanzando metodo: redirectToLogin()");
		LOG.info("Returning a la vista: login");
		return ConstantController.REDIRECT_LOGIN;
	}

	/**
	 * Show login form.
	 *
	 * @param model
	 *            the model
	 * @param error
	 *            the error
	 * @param logout
	 *            the logout
	 * @param request
	 *            the request
	 * @return the string
	 */
	@GetMapping(ConstantController.LOGIN)
	public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout, HttpServletRequest request) {
		LOG.info("Lanzando metodo: showLoginForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		request.getSession().setAttribute("LOGGED-DATA", null);
		LOG.info("Returning a la vista: login");
		return ConstantView.CONTACT_LOGIN;
	}

	/**
	 * Login check and view table with animals when ok or login new when error.
	 *
	 * @param model
	 *            the model
	 * @param contact
	 *            the contact
	 * @param request
	 *            the request
	 * @return the string
	 */
	@PostMapping(ConstantController.LOGIN_CHECK)
	public String login(Model model, @ModelAttribute(name = "userCredentials") Contact contact,
			HttpServletRequest request) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().isEmpty()) {
			for (Contact user : contactServiceImpl.findByAll()) {
				if (contact.getEmail().equals(user.getEmail())
						&& DigestUtils.md5Hex(contact.getPasswd()).equals(user.getPasswd())
						&& user.getActivation() == 1) {
					request.getSession().setAttribute("LOGGED-DATA", contact);
					LOG.info("Returning a la vista: contacts");
					model.addAttribute("animals", animalServiceImpl.findByAll());
					double precio = 0;
					int cantidad = 0;
					for (Animals animal : animalServiceImpl.findByAll()) {
						precio = precio + animal.getEuros();
						cantidad = cantidad + animal.getNumero();
					}
					model.addAttribute("precio", precio);
					model.addAttribute("cantidad", cantidad);
					return ConstantView.ANIMALS_FORM_TABLE;
				}
			}
		}
		LOG.info("Returning a la vista: login?error");
		return ConstantController.REDIRECT_ERROR_LOGIN;
	}

	/**
	 * Show add contact form.
	 *
	 * @param model
	 *            the model
	 * @param error
	 *            the error
	 * @param logout
	 *            the logout
	 * @return the string
	 */
	@GetMapping(ConstantController.ADD_CONTACT)
	public String showAddContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		LOG.info("Returning a la vista: addcontact");
		return ConstantView.ADD_CONTACT_FORM;
	}

	/**
	 * Adds the contact check.
	 *
	 * @param contact
	 *            the contact
	 * @return the string
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TemplateException
	 *             the template exception
	 */
	@PostMapping(ConstantController.ADD_CONTACT_CHECK)
	public String addContact(@ModelAttribute(name = "userCredentials") Contact contact)
			throws MessagingException, IOException, TemplateException {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByAll();
			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail())) {
					LOG.info("PARAMETROS: email=" + contact.getEmail() + ", email-introducido: " + user.getEmail()
							+ "'.");
					return ConstantController.REDIRECT_ERROR_LOGIN;
				}
			}
			contact.setActivation(0);
			LOG.info("Rest method: setActivation(0)");
			String encriptMD5 = DigestUtils.md5Hex(contact.getPasswd());
			contact.setPasswd(encriptMD5);
			LOG.info("Rest method: md5Hex(paswd)");
			contactServiceImpl.addContact(contact);
			LOG.info("Rest method: addContact()");
			mailServiceImpl.sendSimpleMessageHTMLP(contact.getEmail(), contact.getId());
			return ConstantView.ADD_CONTACT_FORM_CHECK;
		}
		return ConstantController.REDIRECT_ERROR_LOGIN;
	}

	/**
	 * Show form reset passwd.
	 *
	 * @param model
	 *            the model
	 * @param error
	 *            the error
	 * @param logout
	 *            the logout
	 * @return the string
	 */
	@GetMapping(ConstantController.RESET_PASSWD)
	public String showResetPasswd(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		LOG.info("Returning a la vista: addcontact");
		return ConstantView.RESET_PASSWD;
	}

	/**
	 * Send mail whith reset passwd.
	 *
	 * @param contact
	 *            the contact
	 * @return the string
	 */
	@PostMapping(ConstantController.RESET_PASSWD_CHECK)
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
				} else {
					return ConstantController.REDIRECT_ERROR_LOGIN;
				}
			}
		}
		return ConstantView.RESET_PASSWD_CHECK;
	}
}