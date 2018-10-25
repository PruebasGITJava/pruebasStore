package com.store.tropifaunia.services;

import org.springframework.stereotype.Component;

import com.store.tropifaunia.entity.Contact;

/**
 * The Interface ContactService.
 */
@Component("contactService")
public interface ContactService {

	public abstract String updateContact(Contact contact, String nombre, String appellidos, int edad, String localidad);

	public abstract String updatePasswd(Contact contact, String passwd);

	public abstract String updateEmail(Contact contact, String email);

	public abstract String updateActivation(Contact contact);

	public abstract String updateDesactivation(Contact contact);

}
