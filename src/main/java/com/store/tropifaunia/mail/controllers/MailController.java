package com.store.tropifaunia.mail.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.mail.constants.RestMailConstants;
import com.store.tropifaunia.mail.service.impl.MailServiceImpl;
import com.store.tropifaunia.services.impl.ContactServiceImpl;

@RestController
@RequestMapping(RestMailConstants.MAIL_REST_SUFIX)
public class MailController extends MailServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactServiceImpl contactServiceImpl;

	/**
	 * Modify activation.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	@GetMapping(RestMailConstants.MAIL_REST_ACTIVATION)
	public String modifyActivation(@RequestParam(required = true, name = "id") int id) {
		Contact contact = contactServiceImpl.findById1(id);
		if (contact != null && contact.getActivation() == 0) {
			contactServiceImpl.updateActivation(contact);
			LOGGER.info("Rest method: updateActivation(0)");
			return ResponseEntity.ok(HttpStatus.OK + " Se ha activado su cuenta correctamente.").toString();
		}
		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED + " Error de activacion del usuario").toString();
	}

}
