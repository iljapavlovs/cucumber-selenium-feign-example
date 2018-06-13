package com.companyname.at.stepdefs.rest;

import com.companyname.at.support.TestDataContext;
import com.companyname.at.support.rest.PetStoreClient;
import com.companyname.at.support.rest.clients.InventoryApi;
import cucumber.api.java.en.When;
import feign.Response;


public class StoreRestSteps {
    private PetStoreClient petStoreClient = new PetStoreClient();
    private InventoryApi inventoryClient = petStoreClient.createInventoryClient();
    private TestDataContext testData = TestDataContext.getInstance();

    @When("^REST - Get store inventory$")
    public void restGetStoreInventory() throws Throwable {
        Response inventory = inventoryClient.getInventory();
        testData.setResponse(inventory);
    }


}
