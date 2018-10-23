package com.store.tropifaunia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.store.tropifaunia.entity.Contact;
import com.store.tropifaunia.repositories.LoginRepository;
import com.store.tropifaunia.services.ContactService;

/**
 * The Class ContactServiceImpl.
 */
@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {

	/** The login repository. */
	@Autowired
	@Qualifier("loginRepository")
	private LoginRepository loginRepository;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#updateActivation(com.rest.entity.
	 *      Contact)
	 */
	@Override
	public String updateActivation(Contact contact) {

		contact.setActivation(1);
		loginRepository.save(contact);
		return " ";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#updateDesactivation(com.rest.entity.
	 *      Contact)
	 */
	@Override
	public String updateDesactivation(Contact contact) {

		contact.setActivation(0);
		loginRepository.save(contact);
		return " ";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#findByAll()
	 */
	@Override
	public List<Contact> findByAll() {
		return loginRepository.findAll();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#findByNombreOrderById(java.lang.String)
	 */
	@Override
	public List<Contact> findByNombreOrderById(String nombre) {
		return loginRepository.findAll();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#addContact(com.rest.entity.Contact)
	 */
	@Override
	public Contact addContact(Contact contact) {
		return loginRepository.save(contact);

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#removeContact(com.rest.entity.Contact)
	 */
	@Override
	public void removeContact(Contact contact) {
		loginRepository.delete(contact);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#updateContact(com.rest.entity.Contact,
	 *      java.lang.String, java.lang.String, int, java.lang.String)
	 */
	@Override
	public String updateContact(Contact contact, String nombre, String appellidos, int edad, String localidad) {

		contact.setNombre(nombre);
		contact.setAppellidos(appellidos);
		contact.setEdad(edad);
		contact.setLocalidad(localidad);

		loginRepository.save(contact);
		return " ";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#findById(int)
	 */
	@Override
	public Optional<Contact> findById(int id) {
		return loginRepository.findById(id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#findById1(int)
	 */
	@Override
	public Contact findById1(int id) {
		return loginRepository.findById(id).get();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#updatePasswd(com.rest.entity.Contact,
	 *      java.lang.String)
	 */
	@Override
	public String updatePasswd(Contact contact, String passwd) {
		contact.setPasswd(passwd);
		loginRepository.save(contact);
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.rest.services.ContactService#updateEmail(com.rest.entity.Contact,
	 *      java.lang.String)
	 */
	@Override
	public String updateEmail(Contact contact, String email) {
		contact.setEmail(email);
		loginRepository.save(contact);
		return null;
	}

}
