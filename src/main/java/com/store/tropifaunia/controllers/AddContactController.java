package com.store.tropifaunia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.tropifaunia.constants.ConstantController;
import com.store.tropifaunia.constants.ConstantView;
import com.store.tropifaunia.model.ContactModel;

@Controller
@RequestMapping("/store")
public class AddContactController {

	@GetMapping("/cancel")
	public String cancel() {
		ConstantController.LOG2.info("Lanzando metodo: cancel()");
		ConstantController.LOG2.info("Returning a la vista: contacts");
		return ConstantView.CONTACT_LOGIN;
	}

	@GetMapping("/contactform")
	public String redirectContactForm(Model model) {
		ConstantController.LOG2.info("Lanzando metodo: redirectContactForm()");
		model.addAttribute("contactModel", new ContactModel());
		ConstantController.LOG2.info("Returning a la vista: contactform");
		return ConstantView.ADDCONTACT;
	}

	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute(name = "contactModel") ContactModel contactModel, Model model) {
		ConstantController.LOG2.info("Lanzando metodo: addContact() -- PARAMETROS: " + contactModel.toString());
		model.addAttribute("result", 1);
		ConstantController.LOG2.info("Returning a la vista: contacts");
		return ConstantView.CONTACT_LOGIN;
	}
}
