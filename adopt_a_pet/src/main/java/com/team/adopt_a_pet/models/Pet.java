package com.team.adopt_a_pet.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team.adopt_a_pet.valiations.ContactValues;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "pets")
@ContactValues.List({
	@ContactValues(
		field1 = "organization",
		field2 = "number",
		field3 = "email",
		message = "At least some contact info is needed",
		field="Contact Info"
	)
})
public class Pet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String ownerName;
	private String email;
	private String number;
	@NotBlank
	private String name;
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name="description", length=1024)
	private String description;
	private Double longitude;
	private Double latitude;
    private String city;
    private String state;
    private String postalCode;
    // Many To One Relationship w/ AgeGroup
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="agegroup_id")
    private AgeGroup ageGrp;
    private String ageString;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private Boolean isBirthDateExact;
    private String breedString;
    // Two Many To One Relationship w/ Breed
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="breedprimary_id")
    private Breed breedPrimary;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="breedsecondary_id")
    private Breed breedSecondary;
//    "breedPrimaryId": 24,
    private Boolean isBreedMixed;
    private String coatLength;
//    private String descriptionText;
//    private Integer pictureCount;
    private String pictureThumbnailUrl;
    private String rescueId;
    private String sex;
    private String sizeGroup;
    private String slug;
    private String trackerimageUrl;
    // One To Many Relationship w/ Organization
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="organization_id")
    private Organization organization;
    // One To Many Relationship w/ Species
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="species_id")
    private Species species;


	// This will not allow the createdAt column to be updated after creation
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @Override
    public String toString() {
    	return "Pet{id: "+id+"|Rescue Id: "+rescueId+"|name: "+name+"|Species: "+species+"|AgeGroup: "+ageGrp+"|BreedP: "+breedPrimary+"|Org: "+organization+"}";
    }
    
    //runs the method(get dates) right before the object is created
    @PrePersist 
    protected void onCreate(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
	//runs a method when the object is modified
    @PreUpdate
    protected void onUpdate(){
    	this.updatedAt = new Date();
    }
    
    //Empty Constructor
    public Pet() {
    }
    
    public Pet(String name, String description, 
    		String city, String state, String postalCode, String pictureThumbnailUrl) {
    	this.name = name;
    	this.description = description;
    	this.city = city;
    	this.state = state;
    	this.postalCode = postalCode;
    	this.pictureThumbnailUrl=pictureThumbnailUrl;
//    	this.ageString = ageString;
    	
    }
    
    //Getters and Setters
    
    
	public Long getId() {
		return id;
	}
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAgeString() {
		return ageString;
	}
	public void setAgeString(String ageString) {
		this.ageString = ageString;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Boolean getIsBirthDateExact() {
		return isBirthDateExact;
	}
	public void setIsBirthDateExact(Boolean isBirthDateExact) {
		this.isBirthDateExact = isBirthDateExact;
	}
	public String getBreedString() {
		return breedString;
	}
	public void setBreedString(String breedString) {
		this.breedString = breedString;
	}
	@JsonProperty
	public Breed getBreedPrimary() {
		return breedPrimary;
	}
	@JsonIgnore
	public void setBreedPrimary(Breed breedPrimary) {
		this.breedPrimary = breedPrimary;
	}
	public Boolean getIsBreedMixed() {
		return isBreedMixed;
	}
	public void setIsBreedMixed(Boolean isBreedMixed) {
		this.isBreedMixed = isBreedMixed;
	}
	public String getCoatLength() {
		return coatLength;
	}
	public void setCoatLength(String coatLength) {
		this.coatLength = coatLength;
	}
//	public String getDescriptionText() {
//		return descriptionText;
//	}
//	public void setDescriptionText(String descriptionText) {
//		this.descriptionText = descriptionText;
//	}
	public String getPictureThumbnailUrl() {
		return pictureThumbnailUrl;
	}
	public void setPictureThumbnailUrl(String pictureThumbnailUrl) {
		this.pictureThumbnailUrl = pictureThumbnailUrl;
	}
	public String getRescueId() {
		return rescueId;
	}
	public void setRescueId(String rescueId) {
		this.rescueId = rescueId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSizeGroup() {
		return sizeGroup;
	}
	public void setSizeGroup(String sizeGroup) {
		this.sizeGroup = sizeGroup;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getTrackerimageUrl() {
		return trackerimageUrl;
	}
	public void setTrackerimageUrl(String trackerimageUrl) {
		this.trackerimageUrl = trackerimageUrl;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public AgeGroup getAgeGrp() {
		return ageGrp;
	}
	public void setAgeGrp(AgeGroup ageGrp) {
		this.ageGrp = ageGrp;
	}
	@JsonProperty
	public Breed getBreedSecondary() {
		return breedSecondary;
	}
	@JsonIgnore
	public void setBreedSecondary(Breed breedSecondary) {
		this.breedSecondary = breedSecondary;
	}
	@JsonProperty
	public Organization getOrganization() {
		return organization;
	}
	@JsonIgnore
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@JsonProperty
	public Species getSpecies() {
		return species;
	}
	@JsonIgnore
	public void setSpecies(Species species) {
		this.species = species;
	}
}
