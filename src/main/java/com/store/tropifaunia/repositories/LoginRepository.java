package com.store.tropifaunia.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.tropifaunia.entity.Contact;

/**
 * The Interface LoginRepository.
 */

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<Contact, Serializable> {

}
