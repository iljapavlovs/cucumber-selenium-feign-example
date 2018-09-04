package com.companyname.at.mock;

import static com.companyname.at.config.Constants.MOCK_PORT;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.common.ClasspathFileSource;

public class PetStoreMock extends AbstractServiceMock{

  public void start(){
    start(MOCK_PORT, new ClasspathFileSource("mock"));

  }

  public void mockDeposit(String urlPath, boolean success) {
    mock.stubFor(
        post(urlPathEqualTo(urlPath))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile(success ? "response_success.json" : "response_failure.json")
            ));
  }

}
