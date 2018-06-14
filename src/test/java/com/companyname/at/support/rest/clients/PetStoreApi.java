package com.companyname.at.support.rest.clients;

import com.companyname.at.model.rest.PetDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface PetStoreApi {

    /**
     * return type is Respose which has all metadata of the response - headers, body, status code, ect
      *
     */
    @RequestLine("GET /store/inventory")
    @Headers({"Content-Type: application/json"})
    Response getInventory();

    /**
     * return type is PetDto DTO which is the model of the response body itself
     *
     */
    @RequestLine("POST /pet")
    @Headers({"Content-Type: application/json"})
    PetDto createPet(PetDto petDto);
}