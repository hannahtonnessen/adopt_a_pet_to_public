package com.team.adopt_a_pet.controllers;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Binding;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.team.adopt_a_pet.models.AgeGroup;
import com.team.adopt_a_pet.models.Breed;
import com.team.adopt_a_pet.models.Organization;
import com.team.adopt_a_pet.models.Pet;
import com.team.adopt_a_pet.models.Species;
import com.team.adopt_a_pet.models.Test;
import com.team.adopt_a_pet.services.AgeGroupService;
import com.team.adopt_a_pet.services.BreedService;
import com.team.adopt_a_pet.services.OrganizationService;
import com.team.adopt_a_pet.services.PetService;
import com.team.adopt_a_pet.services.SpeciesService;

@RestController //parses object and turns into json for you and then sends it off.
public class PetController {
	@Autowired
	public PetService petServ;
	@Autowired
	public OrganizationService orgServ;
	@Autowired
	public BreedService breedServ;
	@Autowired
	public SpeciesService speciesServ;
	@Autowired
	public AgeGroupService ageGroupServ;
	@Autowired
	private Validator validator;
	
	@RequestMapping("/getpets")
	public List<Test> test() {
		List<Test> test = new ArrayList<>(); //arraylist inherits from list
		Test dog = new Test();
		dog.setName("daisy");
		Test cat = new Test();
		cat.setName("Clover");
		test.add(dog);
		test.add(cat);
		return test;
		//grab object data and convert to json
		// return json object as a string
	}
	@RequestMapping("/getfakepet")
	public Pet displayFakePet() {
		Pet newPet = petServ.createFakePet();
		return newPet;
	}
	
	//Create and add dummy entities to database
	@RequestMapping("/dummy/organization")
	public List<Organization> createOrgToDataBase() {
		orgServ.addDummyOrg();
		return orgServ.getAllOrganizations();
	}
	@RequestMapping("/dummy/breed")
	public List<Breed> createBreedToDataBase() {
		breedServ.addDummyBreeds();
		return breedServ.getAllBreeds();
	}
	@RequestMapping("/dummy/species")
	public List<Species> createSpeciesToDataBase() {
		speciesServ.addDummySpecies();
		return speciesServ.getAllSpeciess();
	}
	@RequestMapping("/dummy/agegroups")
	public List<AgeGroup> createAgeGroupsToDataBase() {
		ageGroupServ.addDummyAgeGroups();
		return ageGroupServ.getAllAgeGroups();
	}
	@RequestMapping("/dummy/pets")
	public List<Pet> createPetsToDataBase() {
		petServ.dummyPetToDataBase();
		return petServ.getAllPets();
	}
	
	@RequestMapping("/dummy/all")
	public List<Pet> createDummiesToDataBase() {
		orgServ.addDummyOrg();
		speciesServ.addDummySpecies();
		ageGroupServ.addDummyAgeGroups();
		breedServ.addDummyBreeds();
		petServ.dummyPetToDataBase();
		return petServ.getAllPets();
	}
	
	//Get All Pets
	@RequestMapping("/pets/all")
	public List<Pet> getPets(){
		return petServ.getAllPets();
	}
	//Get Specific Pet By ID
	@RequestMapping("/pets/{pid}")
	public Pet getPets(@PathVariable Long pid){
		Pet x=petServ.getPet(pid);
		System.out.println(x);
		return x;
	}
	//Get All Pets of a Specific AgeGroup
	@RequestMapping("/pets/agegroup/{age}")
	public List<Pet> getAllPetsOfAgeGroup(@PathVariable String age){
		AgeGroup thisAgeGroup = ageGroupServ.getAgeGroupByName(age);
		return thisAgeGroup.getPets();
	}
	//Get All Breeds of a Specific Species
	@RequestMapping("/breeds/species/{sp_id}")
	public List<Breed> getBreedsBySpecies(@PathVariable Species sp_id){
		return breedServ.getBreedsOfSpecies(sp_id);
	}
	//Create new Pet
	@PostMapping("/pets/new")
	public Pet createPet(@RequestBody Pet p) throws ResponseStatusException{//Pet p is information from submitted pet form
		Pet x=mkPet(p);
		x=petServ.getPet(x.getId());
		return x;
	}
	//inspectPetandCreate
	public Pet mkPet(Pet p){
		Pet x=null;
		DataBinder binder=new DataBinder(p);
		binder.setValidator(validator);
		binder.validate();
		BindingResult res=binder.getBindingResult();
		if(!res.hasErrors()) { //below trying to figure out why data that is coming back is null
			x=petServ.saveAndFlushPet(p);
			x=petServ.getPet(x.getId());
//			System.out.println(x);
		}
		else {
			System.out.println("Error");
			System.out.println(res.getAllErrors());
//			System.out.println(p);
		}
		return x;
	}
	//inspectBreedandCreate
	public Breed mkBreed(Breed b){
		Breed x=null;
		DataBinder binder=new DataBinder(b);
		binder.setValidator(validator);
		binder.validate();
		BindingResult res=binder.getBindingResult();
		if(!res.hasErrors()) { //below trying to figure out why data that is coming back is null
			x=breedServ.createBreed(b);
//			x=petServ.getPet(x.getId());
//				System.out.println(x);
		}
		else {
			System.out.println("Error");
			System.out.println(res.getAllErrors());
//				System.out.println(p);
		}
		return x;
	}
	
	@RequestMapping("/breeds/load")
	public Boolean loadBreed(){
		try {
			String urlString="https://api.rescuegroups.org/v5/public/animals/breeds/search/cats/";
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", APIKey);
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setInstanceFollowRedirects(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			String requestResults=content.toString();
			System.out.println(requestResults);
			in.close();
			con.disconnect();
			Pattern pattern = Pattern.compile("\"count\":[0-9]+");
			Matcher matcher = pattern.matcher(requestResults);
			String limit="";
			if (matcher.find()) {
				limit=matcher.group(0);
			}
			limit=limit.split(":")[1];
			
			urlString="https://api.rescuegroups.org/v5/public/animals/breeds/search/cats/?limit="+limit;
			url=new URL(urlString);
			con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", APIKey);
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setInstanceFollowRedirects(false);
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			requestResults=content.toString();
			String tArr=requestResults.split("\"},\"data\":")[1];
			System.out.println(tArr);
			breedServ.parseBreed(tArr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
//    @RequestMapping("/getPets")
//    public String getPets() {
//    	try {
//			URL url = new URL("https://api.rescuegroups.org/v5/public/animals/search/available/cats/haspic?fields[cats]=distance&include=breeds,locations&sort=random&limit=1");
////			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setRequestMethod("GET");
////            conn.connect();
//			System.out.println(url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "pets";
//    
//    	}
    }
