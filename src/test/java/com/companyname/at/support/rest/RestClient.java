package com.companyname.at.support.rest;


import com.companyname.at.config.ApplicationProperties;
import com.companyname.at.config.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import static feign.Retryer.NEVER_RETRY;

@Slf4j
public abstract class RestClient {

    private String proxyHost;
    private int proxyPort;

    public Feign.Builder getCommonBuilder() {
        Feign.Builder feignBuilder = Feign.builder();
        if (ApplicationProperties.getBoolean(proxyEnabledProperty())) {
            OkHttpClient proxyClient = createProxyClient();
            feignBuilder.client(new feign.okhttp.OkHttpClient(proxyClient));
        }
        feignBuilder
                .retryer(NEVER_RETRY)
                .options(new Request.Options(Constants.REST_CLIENT_READ_TIMEOUT * 1000, Constants.REST_CLIENT_READ_TIMEOUT * 1000));

        return feignBuilder;
    }

    protected ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    private OkHttpClient createProxyClient() {
        proxyHost = ApplicationProperties.getString(proxyHostProperty());
        proxyPort = ApplicationProperties.getInteger(proxyPortProperty());

        log.info("Using proxy for Rest Client - " + proxyHost + ":" + String.valueOf(proxyPort));

        return new OkHttpClient.Builder()
                .connectTimeout(Constants.WAIT_SMALL, TimeUnit.SECONDS)
                .writeTimeout(Constants.WAIT_SMALL, TimeUnit.SECONDS)
                .readTimeout(Constants.WAIT_SMALL, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
                .build();

    }

    protected ApplicationProperties.ApplicationProperty proxyEnabledProperty() {
        return ApplicationProperties.ApplicationProperty.REST_PROXY_ENABLED;
    }

    protected ApplicationProperties.ApplicationProperty proxyHostProperty() {
        return ApplicationProperties.ApplicationProperty.PROXY_HOST;
    }

    protected ApplicationProperties.ApplicationProperty proxyPortProperty() {
        return ApplicationProperties.ApplicationProperty.PROXY_PORT;
    }
}
