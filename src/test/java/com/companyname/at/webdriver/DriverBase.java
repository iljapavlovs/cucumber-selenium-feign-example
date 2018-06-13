package com.companyname.at.webdriver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DriverBase {
    //this pool is redundant since it will be created and destroyed before and after each test
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());
    private static ThreadLocal<DriverFactory> driverFactory;

    public static void instantiateDriverObject() {
        driverFactory = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory driverFactory = new DriverFactory();
                webDriverThreadPool.add(driverFactory);
                return driverFactory;
            }
        };
    }

    public static WebDriver getDriver() {
        if (!isWebdriverInitialized()) {
            instantiateDriverObject();
        }
        return driverFactory.get().getDriver();
    }

    public static boolean isWebdriverInitialized() {
        return driverFactory != null;
    }


    public static void closeDriverObjects() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            try {
                driverFactory.quitDriver();
            } catch (Exception e) {
                log.error("driver close failed");
            }
        }
        driverFactory = null;
    }
}