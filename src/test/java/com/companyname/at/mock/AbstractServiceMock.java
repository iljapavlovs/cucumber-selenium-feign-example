package com.companyname.at.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.google.common.base.Preconditions.checkArgument;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.VerificationException;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.github.tomakehurst.wiremock.verification.NearMiss;
import java.util.List;

public abstract class AbstractServiceMock {

  protected WireMockServer mock;

  protected void start(int port, FileSource definitionFileSource) {
    checkArgument(port > 0, "Invalid port");
    mock = new WireMockServer(
        options()
//            .dynamicPort() - any available port
            .port(port)
            .fileSource(definitionFileSource)
            /*
            http://wiremock.org/docs/extending-wiremock/
            By default transformations will be applied globally.
            If you only want them to apply in certain cases you can refer to make them non-global by adding this to your transformer class:
              @Override
              public boolean applyGlobally() {
                return false;
              }


              Then you add the transformation to specific stubs via its name:
              stubFor(get(urlEqualTo("/local-transform")).willReturn(aResponse()
                  .withStatus(200)
                  .withBody("Original body")
                  .withTransformers("my-transformer", "other-transformer")));
            */
            .extensions(new FreemarkerTemplateTransformer())
    );
    mock.start();
  }

  public final void destroy() {
    mock.stop();
  }

  public final void simulateFailure() {
    mock.stubFor(
        post(urlMatching("/.*"))
            .willReturn(aResponse()
                .withStatus(500)
            ));

    mock.stubFor(
        get(urlMatching("/.*"))
            .willReturn(aResponse()
                .withStatus(500)
            ));
  }

  public final void resetAllStubs() {
    mock.resetAll();
  }

  public final void verifyNoRequestMismatches() {
    List<LoggedRequest> allUnmatchedRequests = mock.findUnmatchedRequests().getRequests();

    if (!allUnmatchedRequests.isEmpty()) {
      throw VerificationException.forUnmatchedRequests(allUnmatchedRequests);
    }
    // Unfortunately, no nice Wiremock throwable for "far misses"
    assertThat(allUnmatchedRequests).as("Received yet unbound requests to %s mock", this.getClass()).isEmpty();
  }

}