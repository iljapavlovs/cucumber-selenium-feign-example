package com.companyname.at.stepdefs.rest;

import static com.companyname.at.config.Constants.MOCK_PORT;

import com.companyname.at.support.TestDataContext;
import com.companyname.at.support.rest.PetStoreClient;
import com.companyname.at.support.rest.clients.PetStoreApi;
import com.companyname.at.support.rest.swaggercodegen.SwaggerCodeGenHttpClient;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import feign.Response;
import io.swagger.client.ApiClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class StoreRestSteps {

  private PetStoreClient petStoreClient = new PetStoreClient();
  private PetStoreApi inventoryClient = petStoreClient.createInventoryClient();

  private SwaggerCodeGenHttpClient swaggerCodeGenHttpClient = new SwaggerCodeGenHttpClient();

  private TestDataContext testData = TestDataContext.getInstance();

  @When("^REST - Get store inventory$")
  public void restGetStoreInventory() throws Throwable {
    Response inventory = inventoryClient.getInventory();
    testData.setResponse(inventory);
  }

  @When("^SWAGGER-CODEGEN REST - Get store inventory$")
  public void restMOCKGetStoreInventory() throws Throwable {
    retrofit2.Response<Map<String, Integer>> inventory = swaggerCodeGenHttpClient
        .getInventory();

    testData.setResponseRetrofit(inventory);

  }


  private void sendGet(String urlPath) throws Exception {

    String url = "http://localhost:" + MOCK_PORT + urlPath;

    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    //print result
    System.out.println(response.toString());

  }

  @When("^REST - Get (.*)$")
  public void restGetPet(String urlPath) throws Throwable {
    sendGet(urlPath);
  }
}
