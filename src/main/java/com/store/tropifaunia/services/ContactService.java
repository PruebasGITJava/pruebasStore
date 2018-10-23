package com.store.tropifaunia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.store.tropifaunia.entity.Contact;

/**
 * The Interface ContactService.
 */
@Component("contactService")
public interface ContactService {

	/**
	 * Find by nombre order by id.
	 *
	 * @param nombre
	 *            the nombre
	 * @return the list
	 */
	public abstract List<Contact> findByNombreOrderById(String nombre);

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the optional
	 */
	public abstract Optional<Contact> findById(int id);

	/**
	 * Adds the contact.
	 *
	 * @param contact
	 *            the contact
	 * @return the contact
	 */
	public abstract Contact addContact(Contact contact);

	/**
	 * Removes the contact.
	 *
	 * @param contact
	 *            the contact
	 */
	public abstract void removeContact(Contact contact);

	/**
	 * Update contact.
	 *
	 * @param contact
	 *            the contact
	 * @param nombre
	 *            the nombre
	 * @param appellidos
	 *            the appellidos
	 * @param edad
	 *            the edad
	 * @param localidad
	 *            the localidad
	 * @return the string
	 */
	public abstract String updateContact(Contact contact, String nombre, String appellidos, int edad, String localidad);

	/**
	 * Update passwd.
	 *
	 * @param contact
	 *            the contact
	 * @param passwd
	 *            the passwd
	 * @return the string
	 */
	public abstract String updatePasswd(Contact contact, String passwd);

	/**
	 * Update email.
	 *
	 * @param contact
	 *            the contact
	 * @param email
	 *            the email
	 * @return the string
	 */
	public abstract String updateEmail(Contact contact, String email);

	/**
	 * Update activation.
	 *
	 * @param contact
	 *            the contact
	 * @return the string
	 */
	public abstract String updateActivation(Contact contact);

	/**
	 * Find by id 1.
	 *
	 * @param id
	 *            the id
	 * @return the contact
	 */
	public abstract Contact findById1(int id);

	/**
	 * Find by all.
	 *
	 * @return the list
	 */
	public abstract List<Contact> findByAll();

	public abstract String updateDesactivation(Contact contact);

}
