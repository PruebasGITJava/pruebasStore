package com.store.tropifaunia.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.tropifaunia.entity.Animals;

@Repository("animalsRepository")
public interface AnimalsRepository extends JpaRepository<Animals, Serializable> {

}
