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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.entity.Animals;
import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.mail.service.impl.MailServiceImpl;
import com.store.tropifaunia.model.ContactModel;
import com.store.tropifaunia.services.impl.AnimalServiceImpl;
import com.store.tropifaunia.services.impl.ContactServiceImpl;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/contacts")
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

	@GetMapping("/cancel")
	public String cancel() {
		ConstantController.LOG2.info("Lanzando metodo: cancel()");
		ConstantController.LOG2.info("Returning a la vista: contacts");
		return ConstantView.CONTACTS;
	}

	@GetMapping("/contactform")
	public String redirectContactForm(Model model) {
		ConstantController.LOG2.info("Lanzando metodo: redirectContactForm()");
		model.addAttribute("contactModel", new ContactModel());
		ConstantController.LOG2.info("Returning a la vista: contactform");
		return ConstantView.CONTACT_FORM;
	}

	@ModelAttribute("animals")
	public List<Animals> getUserList() {
		List<Animals> animals = animalServiceImpl.findByAll();
		return animals;
	}

	// @PostMapping("/addcontact")
	// public String addContact(@ModelAttribute(name = "contactModel")
	// ContactModel contactModel, Model model) {
	// ConstantController.LOG2.info("Lanzando metodo: addContact() --
	// PARAMETROS: " + contactModel.toString());
	// model.addAttribute("result", 1);
	// ConstantController.LOG2.info("Returning a la vista: contacts");
	// return ConstantView.CONTACTS;
	// }

	@GetMapping(ConstantController.EDITPERFIL)
	public String showEditContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ConstantController.LOG
				.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		ConstantController.LOG.info("Returning a la vista: editperfil");
		return ConstantView.EDITPERFIL;
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

					return ConstantView.CONTACT_FORM;
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

	@PostMapping("/datePass")
	public String updatePasswd(@RequestBody Contact contact) {
		if (!contact.getEmail().trim().isEmpty() && !contact.getPasswd().trim().isEmpty()) {
			List<Contact> contactos = contactServiceImpl.findByNombreOrderById(contact.getNombre());

			for (Contact user : contactos) {
				if (contact.getEmail().equals(user.getEmail()) && contact.getPasswd().length() > 8) {

					contactServiceImpl.updatePasswd(user, DigestUtils.md5Hex(contact.getPasswd()));

					return ResponseEntity.ok(HttpStatus.OK
							+ " Se ha modificado la contraseña de manera manual del email: " + contact.getEmail())
							.toString();
				}
			}
		}
		return ResponseEntity
				.ok(HttpStatus.UNAUTHORIZED
						+ " Error de cambio contraseña del usuario o no cumple con los requisitos de seguridad.")
				.toString();
	}

}