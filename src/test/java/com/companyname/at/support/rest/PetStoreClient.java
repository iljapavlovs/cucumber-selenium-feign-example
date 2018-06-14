package com.companyname.at.support.rest;


import com.companyname.at.config.ApplicationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.companyname.at.support.rest.clients.PetStoreApi;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PetStoreClient extends RestClient {
    private final String serviceUrl = ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.SERVICE_URL);

    public PetStoreApi createInventoryClient() {
        ObjectMapper mapper = createObjectMapper();
        log.info("Creating REST client with SERVICE_URL - " + serviceUrl);
        return getCommonBuilder()
                .decoder(new JacksonDecoder(mapper))
                .encoder(new JacksonEncoder(mapper))
                .target(PetStoreApi.class, serviceUrl);
    }
}
