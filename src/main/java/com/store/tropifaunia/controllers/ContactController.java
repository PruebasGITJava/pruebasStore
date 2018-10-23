package com.store.tropifaunia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.model.ContactModel;

@Controller
@RequestMapping("/contacts")
public class ContactController {

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

	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute(name = "contactModel") ContactModel contactModel, Model model) {
		ConstantController.LOG2.info("Lanzando metodo: addContact() -- PARAMETROS: " + contactModel.toString());
		model.addAttribute("result", 1);
		ConstantController.LOG2.info("Returning a la vista: contacts");
		return ConstantView.CONTACTS;
	}

	@GetMapping(ConstantController.EDITPERFIL)
	public String showAddContactForm(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ConstantController.LOG
				.info("Lanzando metodo: showAddContactForm() -- PARAMETROS: error= " + error + ", logout: " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new Contact());
		ConstantController.LOG.info("Returning a la vista: addcontact");
		return ConstantView.EDITPERFIL;
	}

}