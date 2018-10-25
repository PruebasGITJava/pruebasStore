package com.store.tropifaunia.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.tropifaunia.entity.Contact;

/**
 * The Interface LoginRepository.
 */

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<Contact, Serializable> {

	public abstract List<Contact> findByNombreOrderById(String nombre);

	public abstract Contact findById(int id);

}
