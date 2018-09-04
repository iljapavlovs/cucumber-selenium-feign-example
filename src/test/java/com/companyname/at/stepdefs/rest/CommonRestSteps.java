package com.companyname.at.stepdefs.rest;

import com.companyname.at.support.TestDataContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import feign.Response;
import org.apache.commons.io.IOUtils;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class CommonRestSteps {

    private TestDataContext testData = TestDataContext.getInstance();

    @Then("^Response - status code is (\\d+)$")
    public void responseStatusCodeIs(int statusCode) throws Throwable {
        assertThat(testData.getResponse().status()).isEqualTo(statusCode);
    }

    @Then("^SWAGGER-CODEGEN Response - status code is (\\d+)$")
    public void responseMockStatusCodeIs(int statusCode) throws Throwable {
        assertThat(testData.getResponseRetrofit().code()).isEqualTo(statusCode);
    }

    @And("^Response - body is$")
    public void responseBodyIs(String expected) throws Throwable {
        Response response = testData.getResponse();
        String actual = IOUtils.toString(response.body().asReader());
        assertThat(actual).isEqualTo(expected);
    }



}
