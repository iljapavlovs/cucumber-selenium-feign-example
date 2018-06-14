package com.companyname.at.stepdefs.rest;

import com.companyname.at.support.TestDataContext;
import com.companyname.at.support.rest.PetStoreClient;
import com.companyname.at.support.rest.clients.PetStoreApi;
import cucumber.api.java.en.When;
import feign.Response;


public class StoreRestSteps {
    private PetStoreClient petStoreClient = new PetStoreClient();
    private PetStoreApi inventoryClient = petStoreClient.createInventoryClient();
    private TestDataContext testData = TestDataContext.getInstance();

    @When("^REST - Get store inventory$")
    public void restGetStoreInventory() throws Throwable {
        Response inventory = inventoryClient.getInventory();
        testData.setResponse(inventory);
    }


}
