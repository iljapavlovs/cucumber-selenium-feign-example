package com.companyname.at.stepdefs.rest;

import static org.awaitility.Awaitility.await;

import com.companyname.at.mock.PetStoreMock;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.util.concurrent.TimeUnit;

public class MockSteps {

  private PetStoreMock petStoreMock = new PetStoreMock();

  @Before("@mock")
  public void setUp() {
    petStoreMock.start();
    petStoreMock.resetAllStubs();
  }


  @After("@mock")
  public void tearDown() {
    petStoreMock.verifyNoRequestMismatches();
    petStoreMock.destroy();

  }

  @Given("^MOCK - mock GET request on \"([^\"]*)\"$")
  public void mockStartMockForGettingInventory(String urlPath) throws Throwable {

    petStoreMock.mockGetPet(urlPath, true);
  }

  @Then("^MOCK - wait until mock is triggered for \"([^\"]*)\" path$")
  public void mockWaitUntilMockIsTriggeredWithCorrectValues(String path) throws Throwable {

    await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
      petStoreMock.verifyPetMockTriggering(path);
    });
  }
}
