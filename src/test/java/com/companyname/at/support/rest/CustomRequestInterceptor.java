package com.companyname.at.support.rest;

import com.google.common.net.HttpHeaders;
import com.companyname.at.support.TestDataContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        TestDataContext testData = TestDataContext.getInstance();

        if (testData.getCurrentUser() != null) {
            String userCookie = "CookieName=" + testData.getCurrentUser();
            requestTemplate.header(HttpHeaders.COOKIE, userCookie);
        }
        //log.info("REST API call - " + requestTemplate.toString()); //enable for full logging
        log.info("REST API call - " + requestTemplate.method() + ":" + requestTemplate.url() + requestTemplate.queryLine());
    }
}