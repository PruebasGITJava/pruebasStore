package com.store.tropifaunia.services.impl;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<Animals> findById(int id) {
		return animalsRepository.findById(id);
	}

	@Override
	public Animals addAnimals(Animals animals) {
		return animalsRepository.save(animals);
	}

	@Override
	public Animals findById1(int id) {
		return animalsRepository.findById(id).get();
	}

	@Override
	public List<Animals> findByAll() {
		return animalsRepository.findAll();
	}

	@Override
	public Animals removeAnimals(Animals animals) {
		// TODO Auto-generated method stub
		return null;
	}

}
