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

/** Example Mocks written in Kotlin

* Create mock for a specific path

    fun mockCustomerDetails(uid: String, customerId: String, success: Boolean, customer: String) {
        mock.stubFor(
            get(urlPathEqualTo("$CUSTOMER_URL_PATH/$uid"))
                .willReturn(
                    aResponse()
                        .withTransformers("freemarker-transformer")
                        .withHeader("Content-Type", "application/json")
                        .withBody(if (success) customer else null)
                        .withTransformerParameter("uid", uid)
                        .withTransformerParameter("customerId", customerId)
                )
        )
    }
    
    
* Verify that previous Mock has been trigerred with specific headers, params, etc

    fun verifyCustomerMockTriggering(uid: String, mockTriggerCount: Int) {
        mock.verify(mockTriggerCount,
            getRequestedFor(urlPathEqualTo("$CUSTOMER_URL_PATH/$uid"))
                .withHeader("skey", equalTo("secretKey"))
                .withHeader("Authorization", equalTo(AUTHORIZATION))
        )
    }
    
    
        fun mockSmsActiveDomain(success: Boolean, body: String) {
        mock.stubFor(
            get(urlPathEqualTo(DOMAIN_SMS_URL_PATH))
                .withQueryParam("skey", matching(".*"))
                .willReturn(
                    aResponse()
                        .withBody(if (success)body else null)

                )
        )
    }


    fun verifyDomainEmailMockTriggering(mockTriggerCount: Int) {
        mock.verify(mockTriggerCount,
            getRequestedFor(urlPathEqualTo(DOMAIN_MAIL_URL_PATH))
                .withQueryParam("skey", equalTo("secretKey"))
                .withQueryParam("format", equalTo("plaintext"))
                .withHeader("skey", equalTo("secretKey"))
                .withHeader("Authorization", equalTo(AUTHORIZATION))
        )
    }
    
        
*/
