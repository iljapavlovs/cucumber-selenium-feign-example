package com.companyname.at.mock;

import static com.companyname.at.config.Constants.MOCK_PORT;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.common.ClasspathFileSource;

public class PetStoreMock extends AbstractServiceMock{

  public void start(){
    start(MOCK_PORT, new ClasspathFileSource("mock"));

  }

  public void mockGetPet(String urlPath, boolean success) {
    mock.stubFor(
        post(urlPathEqualTo(urlPath))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile(success ? "response_success.json" : "response_failure.json")

                // if you dont want transfomer to be applied globally, then you can specify it here
//                .withTransformers("freemarker-transformer")
                // will replace any variable defined with ${uid} with uid value in response_success.json or in response_failure.json
//                .withTransformerParameter("uid", uid)

            ));
  }

  public void verifyPetMockTriggering(String path) {
    mock.verify(
        getRequestedFor(urlPathEqualTo(path))
//            .withQueryParam("format", equalTo("plaintext"))
//            .withHeader("Authorization", equalTo("Basic dsfsdfsdf="))
    );
  }
}
