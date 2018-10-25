package com.store.tropifaunia.services;

import org.springframework.stereotype.Component;

import com.store.tropifaunia.entity.Animals;

@Component("animalService")
public interface AnimalService {

	public abstract Animals updateAnimals(Animals animals, int numero);

}
