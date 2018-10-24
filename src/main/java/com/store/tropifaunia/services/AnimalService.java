package com.store.tropifaunia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.store.tropifaunia.entity.Animals;

@Component("animalService")
public interface AnimalService {

	public abstract Optional<Animals> findById(int id);

	public abstract Animals addAnimals(Animals animals);

	public abstract Animals removeAnimals(Animals animals);

	public abstract Animals findById1(int id);

	public abstract List<Animals> findByAll();

	public abstract Animals updateAnimals(Animals animals, int numero);

}
