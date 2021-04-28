package com.team.adopt_a_pet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.adopt_a_pet.models.Species;
import com.team.adopt_a_pet.repositories.SpeciesRepository;

@Service
public class SpeciesService {
	@Autowired
	private SpeciesRepository speciesRepo;
	
	//create or update
	public Species createSpecies(Species species) {
		return speciesRepo.save(species);
	}
	//retrieve all
	public List<Species> getAllSpeciess(){
		return speciesRepo.findAll();
	}
	//retrieve a species
	public Species getSpecies(Long id) {
		Optional<Species> optionalSpecies = speciesRepo.findById(id);
		return optionalSpecies.orElse(null);
	}
	//delete a species
	public void deleteSpecies(Long id) {
		speciesRepo.deleteById(id);
	}
}
