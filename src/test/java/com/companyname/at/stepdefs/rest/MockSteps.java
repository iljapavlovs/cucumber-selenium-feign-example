package com.companyname.at.stepdefs.rest;

import com.companyname.at.mock.PetStoreMock;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class MockSteps {

  private PetStoreMock petStoreMock = new PetStoreMock();

  @Before("@mock")
  public void setUp() {

  }


  @After
  public void tearDown() {
    petStoreMock.verifyNoRequestMismatches();
    petStoreMock.resetAllStubs();
    petStoreMock.destroy();

  }

  @Given("^MOCK - mock GET request on (.*)$")
  public void mockStartMockForGettingInventory(String urlPath) throws Throwable {
    petStoreMock.start();
    petStoreMock.resetAllStubs();
    petStoreMock.mockDeposit(urlPath, true);
  }
}
