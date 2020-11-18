package com.petstore.petstore.data.repository;

import com.petstore.petstore.data.model.Gender;
import com.petstore.petstore.data.model.Pet;
import com.petstore.petstore.data.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.iterator;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;


    @BeforeEach
    void setUp() {
    }

    //Test that we can sava a pet to the database
    @Test
    public void whenPetIsSaved_thenReturnPetId(){
        //step 1: create an instance of a pet
        Pet pet  = new Pet();
        pet.setName("Jack");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);

        log.info("Pet instance before saving --> {}", pet);
        //2: call repository save method

        petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();
        log.info("Pet instance before saving --> {}", pet);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void whenStoreIsMappedToPet_thenForeignKeyIsPresent(){
        //Create a pet
        Pet newPet = new Pet();
        newPet.setName("Jack");
        newPet.setAge(2);
        newPet.setBreed("Dog");
        newPet.setColor("Black");
        newPet.setPetSex(Gender.MALE);

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");;
        store.setContactNo("08034717043");

        //map to store
        newPet.setStore(store);

        //save pet
        petRepository.save(newPet);


        log.info("Pet Instance after saving --> {}", newPet);
        log.info("Store Instance after saving --> {}", store);


        //assert
        assertThat(newPet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(newPet.getStore()).isNotNull();
    }
    @Test
    @Transactional
    @Rollback(value =false)
    public void whenIAddPetsToAStore_thenICanFetchAListOfPetsFromStore(){

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");;
        store.setContactNo("08034717043");



        //Create a pet
        Pet sally = new Pet();
        sally.setName("Sally");
        sally.setAge(3);
        sally.setBreed("Dog");
        sally.setColor("Brown");
        sally.setPetSex(Gender.FEMALE);
        sally.setStore(store);

        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(5);
        jack.setBreed("Dog");
        jack.setColor("Blue");
        jack.setPetSex(Gender.MALE);
        jack.setStore(store);




        store.addPets(jack);
        store.addPets(sally);

        storeRepository.save(store);


        //save pet
        petRepository.save(jack);
        petRepository.save(sally);

        log.info("Pet Instance after saving --> {}", sally, jack);

        //assert for pet id's
        assertThat(sally.getId()).isNotNull();
        assertThat(jack.getId()).isNotNull();


        //assert for store id's
        assertThat(store.getId()).isNotNull();
        assertThat(store.getPetList()).isNotNull();
    }


    @Test
    public void whenFindAllIsCalled_thenReturnAllPetsInStore(){
        //find all pets from store

        List<Pet> savedPets = petRepository.findAll();

        log.info("Fetched pets list from db --> {}", savedPets);

        //assert that pet exists

        assertThat(savedPets).isNotNull();
        assertThat(savedPets.size()).isEqualTo(6);
    }

    @Test
    public void updateExistingPetDetailsTest(){
        //fetch a pet
        Optional <Pet> sally = petRepository.findById(21);

        //or

        Pet sill = petRepository.findById(21).orElse(null);
        log.info("Fetched pets list from db --> {}", sill);

        //assert the field

        assertThat(sill).isNotNull();
        assertThat(sill.getColor()).isEqualTo("pruple");

        //update pet field
        petRepository.save(sill);
        log.info("Fetched pets list from db --> {}", sill);

        //assert that updated field has change

        assertThat(sill.getColor()).isEqualTo("brown");

    }

    @Test
    public void whenIdeletePetFromDatabase_thenPetIsDelete(){
        //check if pet exist
        boolean jill = petRepository.existsById(31);

        //assert that pet exist
        assertThat(jill).isTrue();

        //delete pet

        petRepository.deleteById(31);


    }
}