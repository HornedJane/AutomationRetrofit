package ru.tinkoff.qa.simple.petstore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.tinkoff.qa.simple.client.petstore.PetStore;
import ru.tinkoff.qa.simple.client.petstore.PetStoreService;
import ru.tinkoff.qa.simple.models.pet.Category;
import ru.tinkoff.qa.simple.models.pet.Pet;

import java.io.IOException;

public class ApiTests {
    private static PetStore petStore;
    private static Pet testPet;

    @BeforeAll
    public static void beforeAll() {
        petStore = new PetStoreService().getPetStore();
        testPet = new Pet();
        testPet.setId(100);
        testPet.setName("Piglet");
        Category category = new Category();
        category.setName("Pig");
        testPet.setCategory(category);
        testPet.setStatus("available");
    }

    @Test
    public void postTest() throws IOException {
        Pet retrofitPet = petStore.createPet(testPet).execute().body();
        Assertions.assertEquals(retrofitPet, testPet);
    }

    @Test
    public void positiveGetTest() throws IOException {
        petStore.createPet(testPet).execute();
        Pet idGetPet = petStore.getPetbyId(testPet.getId()).execute().body();
        Assertions.assertEquals(idGetPet, testPet);
    }

    @Test
    public void negativeGetTest() throws IOException {
        petStore.deletePet(0000).execute();
        Integer response = petStore.getPetbyId(0000).execute().code();
        Assertions.assertEquals(404, response);
    }

    @Test
    public void positivePutTest() throws IOException {
        petStore.createPet(testPet).execute();
        Integer response = petStore.updatePet(testPet.getId(), "Babe", "Unavailable").execute().code();
        Assertions.assertEquals(200, response);
    }

    @Test
    public void negativePutTest() throws IOException {
        petStore.deletePet(0101).execute();
        Integer response = petStore.updatePet(0101, "a", "b").execute().code();
        Assertions.assertEquals(404, response);
    }

    @Test
    public void positiveDeleteTest() throws IOException {
        Pet retrofitPet = petStore.createPet(testPet).execute().body();
        Integer response = petStore.deletePet(retrofitPet.getId()).execute().code();
        Assertions.assertEquals(200, response);
    }

    @Test
    public void negativeDeleteTest() throws IOException {
        petStore.deletePet(0000).execute();
        Integer response = petStore.deletePet(0000).execute().code();
        Assertions.assertEquals(404, response);
    }
}
