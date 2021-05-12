package ru.tinkoff.qa.simple.client.petstore;

import retrofit2.Call;
import retrofit2.http.*;
import ru.tinkoff.qa.simple.models.pet.Pet;
import ru.tinkoff.qa.simple.models.response.ServerMessage;

public interface PetStore {
    @POST("/v2/pet")
    Call<Pet> createPet(@Body Pet pet);

    @GET("/v2/pet/{id}")
    Call<Pet> getPetbyId(@Path("id") Integer id);

    @PUT("/v2/pet/{id}")
    Call<ServerMessage> updatePet(@Path("id") Integer id, @Query("name") String name, @Query("status") String status);

    @DELETE("/v2/pet/{id}")
    Call<ServerMessage> deletePet(@Path("id") Integer id);
}
