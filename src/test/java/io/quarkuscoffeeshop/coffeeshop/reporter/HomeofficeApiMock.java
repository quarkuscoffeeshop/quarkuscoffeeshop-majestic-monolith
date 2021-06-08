package io.quarkuscoffeeshop.coffeeshop.reporter;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class HomeofficeApiMock implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {

        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubFor(post(urlEqualTo("/v1/order"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        return Collections.singletonMap("io.quarkuscoffeeshop.coffeeshop.reporter.RESTService", wireMockServer.baseUrl());     }

    @Override
    public void stop() {
        verify(postRequestedFor(urlEqualTo("/v1/order")).withHeader("Content-Type", equalTo("application/json")));
        wireMockServer.stop();
    }
}
