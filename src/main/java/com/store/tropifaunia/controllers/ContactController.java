package com.store.tropifaunia.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.entity.Animals;
import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.mail.service.impl.MailServiceImpl;
import com.store.tropifaunia.services.impl.AnimalServiceImpl;
import com.store.tropifaunia.services.impl.ContactServiceImpl;

import freemarker.template.TemplateException;

@Controller
@RequestMapping(ConstantController.ANIMALS)
public class ContactController {

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactServiceImpl contactServiceImpl;
	@Autowired
	@Qualifier("mailServiceImpl")
	private MailServiceImpl mailServiceImpl;
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;

	public static final Log LOG = LogFactory.getLog(ContactController.class);

	@GetMapping(ConstantController.CANCEL)
	public String cancel(@ModelAttribute(name = "animals") Animals animals, Model model,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "result", required = false) String result) {
		LOG.info("Lanzando metodo: cancel()");
		LOG.info("Returning a la vista: contacts");
		model.addAttribute("animals", animalServiceImpl.findByAll());
		double precio = 0;
		int cantidad = 0;

		for (Animals animal : animalServiceImpl.findByAll()) {
			precio = precio + animal.getEuros();
			cantidad = cantidad + animal.getNumero();
		}
		model.addAttribute("result", result);
		model.addAttribute("error", error);
		model.addAttribute("precio", precio * cantidad);
		model.addAttribute("cantidad", cantidad);

		return ConstantView.ANIMALS_FORM_TABLE;

	}

	@GetMapping(ConstantController.ANIMALS_FORM_BUY)
	public String ContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: redirectContactForm()");
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("animals", new Animals());
		LOG.info("Returning a la vista: contactform");
		return ConstantView.ANIMALS_FORM_BUY;
	}

	@GetMapping(ConstantController.ANIMALS_FORM_SALE)
	public String ContactFormSale(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: redirectContactForm()");
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("animals", new Animals());
		LOG.info("Returning a la vista: contactform");
		return ConstantView.ANIMALS_FORM_SALE;
	}

	@PostMapping(ConstantController.ADD_ANIMALS_BUY)
	public String addContact(Model model, @ModelAttribute(name = "animals") Animals animals) {
		if (!animals.getNombreRaza().trim().isEmpty() && !animals.getTipo().isEmpty()) {
			for (Animals an : animalServiceImpl.findByAll()) {
				if (animals.getNombreRaza().equals(an.getNombreRaza()) && animals.getTipo().equals(an.getTipo())
						&& animals.getNumero() < 30) {
					LOG.info("Returning a la vista: animals");
					animals.setId(an.getId());
					animals.setEuros(an.getEuros());
					animalServiceImpl.updateAnimals(animals, (an.getNumero() + animals.getNumero()));

					model.addAttribute("animals", animalServiceImpl.findByAll());
					double precio = 0;
					int cantidad = 0;

					for (Animals animal : animalServiceImpl.findByAll()) {
						precio = precio - animal.getEuros();
						cantidad = cantidad + animal.getNumero();
					}
					model.addAttribute("precio", precio * cantidad);
					model.addAttribute("cantidad", cantidad);

					return ConstantController.REDIRECT_OK_BUY;
				}
			}
		}
		LOG.info("Returning a la vista: contact?error");
		return ConstantController.REDIRECT_ERROR_BUY;
	}

	@PostMapping(ConstantController.ADD_ANIMALS_SALE)
	public String addContactSale(Model model, @ModelAttribute(name = "animals") Animals animals) {
		if (!animals.getNombreRaza().trim().isEmpty() && !animals.getTipo().isEmpty()) {
			for (Animals an : animalServiceImpl.findByAll()) {
				if (animals.getNombreRaza().equals(an.getNombreRaza()) && animals.getTipo().equals(an.getTipo())
						&& animals.getNumero() <= an.getNumero()) {
					LOG.info("Returning a la vista: animals");
					animals.setId(an.getId());
					animals.setEuros(an.getEuros());
					animalServiceImpl.updateAnimals(animals, (an.getNumero() - animals.getNumero()));

					model.addAttribute("animals", animalServiceImpl.findByAll());
					double precio = 0;
					int cantidad = 0;

					for (Animals animal : animalServiceImpl.findByAll()) {
						precio = precio + animal.getEuros();
						cantidad = cantidad - animal.getNumero();
					}
					model.addAttribute("precio", precio * cantidad);
					model.addAttribute("cantidad", cantidad);

					return ConstantController.REDIRECT_OK_SALE;
				}
			}
		}
		LOG.info("Returning a la vista: contact?error");
		return ConstantController.REDIRECT_ERROR_SALE;
	}

	@GetMapping(ConstantController.EDIT_PERFIL_CONTACT)
	public String showEditContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		LOG.info("Returning a la vista: editperfil");
		return ConstantView.EDIT_PERFIL_CONTACT;
	}

	@PostMapping("/datePerson")
	public String updateContact1(@RequestBody Contact contact) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByNombreOrderById(contact.getNombre());

			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail())
						&& DigestUtils.md5Hex(contact.getPasswd()).equals(user.getPasswd())) {

					contactServiceImpl.updateContact(user, contact.getNombre(), contact.getAppellidos(),
							contact.getEdad(), contact.getLocalidad());

					return ConstantView.ANIMALS_FORM_BUY;
				}
			}
		}
		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED + " Error de modificación de datos del usuario").toString();
	}

	@PostMapping("/dateEmail")
	public String updateEmail(@RequestBody Contact contact) throws MessagingException, IOException, TemplateException {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByNombreOrderById(contact.getNombre());

			for (Contact user : contactos) {
				if (DigestUtils.md5Hex(contact.getPasswd()).equals(user.getPasswd())) {
					contactServiceImpl.updateDesactivation(user);
					contactServiceImpl.updateEmail(user, contact.getEmail());
					mailServiceImpl.sendSimpleMessageHTMLP(contact.getEmail(), user.getId());
					return ResponseEntity
							.ok(HttpStatus.OK + " Se ha modificado el email de manera manual con nombre actual: '"
									+ contact.getEmail()
									+ "', por su seguridad se ha mandado un correo de activación en su nuevo correo.")
							.toString();
				}
			}
		}
		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED + " Error de cambio de email del usuario").toString();
	}

	@GetMapping(ConstantController.UPDATE_PASSWD)
	public String showUpdatePasswd(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		LOG.info("Returning a la vista: addcontact");
		return ConstantView.RESET_PASSWD;
	}

	@PostMapping(ConstantController.UPDATE_PASSWD_CHECK)
	public String updatePasswd(@ModelAttribute(name = "userCredentials") Contact contact) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByNombreOrderById(contact.getNombre());

			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail()) && contact.getPasswd().length() > 8) {

					contactServiceImpl.updatePasswd(user, DigestUtils.md5Hex(contact.getPasswd()));

					return ConstantController.REDIRECT_OK_SALE;
				}
			}
		}
		return ConstantController.REDIRECT_ERROR_SALE;
	}

}