package com.companyname.at.support;

import feign.Response;
import lombok.Getter;
import lombok.Setter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@Getter
@Setter
public class TestDataContext {

    private static final TestDataContext INSTANCE = new TestDataContext();
    private String currentUser;

    private String[] pdfPages;

    private Response response;
    private retrofit2.Response responseRetrofit;


    private Map<String, Supplier<String>> testDataMap = new HashMap<>();

    private TestDataContext() {
        this.testDataMap.put("ddMMyyy", () -> new SimpleDateFormat("ddMMyyyy").format(new Date()));
        //TODO - cannot use MM/dd/yy as an identifier with current implementation
        this.testDataMap.put("MMddyy", () -> new SimpleDateFormat("MM/dd/yy").format(new Date()));
        this.testDataMap.put("ddMMyyyyHHmmss", () -> new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));
        this.testDataMap.put("green", () -> "rgb(114, 181, 143)");
        this.testDataMap.put("amber", () -> "rgb(255, 169, 66)");
        this.testDataMap.put("red", () -> "rgb(231, 55, 95)");
    }

    public static TestDataContext getInstance() {
        return INSTANCE;
    }
}