package com.store.tropifaunia.services.impl;

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
