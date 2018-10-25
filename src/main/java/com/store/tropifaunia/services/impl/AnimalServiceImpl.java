package com.store.tropifaunia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.store.tropifaunia.entity.Animals;
import com.store.tropifaunia.repositories.AnimalsRepository;
import com.store.tropifaunia.services.AnimalService;

@Service("animalServiceImpl")
public class AnimalServiceImpl implements AnimalService {
	@Autowired
	@Qualifier("animalsRepository")
	private AnimalsRepository animalsRepository;

	@Override
	public Animals updateAnimals(Animals animals, int numero) {
		animals.setNumero(numero);
		return animalsRepository.save(animals);

	}

}
