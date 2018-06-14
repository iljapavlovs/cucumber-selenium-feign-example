package com.companyname.at.config;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Properties;

import static com.companyname.at.config.ApplicationProperties.ApplicationProperty.APP_ENV;

@Slf4j
public class ApplicationProperties {

    private static HashMap<String, Properties> DEFAULT_VALUES = new HashMap<String, Properties>() {
        {
            put("default", new Properties() {
                {
                    //timeout and wait time properties
                    setProperty(ApplicationProperty.WAIT_TIMEOUT_SHT.name, "5");
                    setProperty(ApplicationProperty.WAIT_TIMEOUT.name, "40");
                    setProperty(ApplicationProperty.WAIT_TIMEOUT_LNG.name, "60");
                    setProperty(ApplicationProperty.WAIT_TIMEOUT_VERY_LNG.name, "180");
                    setProperty(ApplicationProperty.REST_CLIENT_READ_TIMEOUT.name, "50");
                    setProperty(ApplicationProperty.UPLOAD_SERVICE_TIMEOUT.name, "50");
                    setProperty(ApplicationProperty.RATING_STATUS_UPDATE_SERVICE_TIMEOUT.name, "50");

                    setProperty(ApplicationProperty.TARGET_BROWSER.name, "FIREFOX"); //PHANTOMJS, OPERA, SAFARI, EDGE, IE, CHROME, FIREFOX

                    //application URL's
                    setProperty(ApplicationProperty.APP_URL.name, "http://google.com");
                    setProperty(ApplicationProperty.SERVICE_URL.name, "http://petstore.swagger.io/v2");

                    //User settings
                    setProperty(ApplicationProperty.USER.name, "");

                    //Proxy settings
                    setProperty(ApplicationProperty.REST_PROXY_ENABLED.name, "false");
                    setProperty(ApplicationProperty.BROWSER_PROXY_ENABLED.name, "false");
                    setProperty(ApplicationProperty.PROXY_HOST.name, "proxy.com");
                    setProperty(ApplicationProperty.PROXY_PORT.name, "8080");

                    //Selenium grid settings
                    setProperty(ApplicationProperty.REMOTE_DRIVER.name, "false");
                    setProperty(ApplicationProperty.SELENIUM_GRID_URL.name, "http://selenium-hub:8080/wd/hub");
                    setProperty(ApplicationProperty.SELENIUM_GRID_VIDEO_CAPTURE_URL.name, "http://selenium-hub:8080/grid/resources/remote?session=");
                    setProperty(ApplicationProperty.SELENIUM_GRID_VIDEO_CAPTURE_ENABLED.name, "true");
                    setProperty(ApplicationProperty.DESIRED_BROWSER_VERSION.name, "");
                    setProperty(ApplicationProperty.DESIRED_PLATFORM.name, "");
                    setProperty(ApplicationProperty.SELENIUM_GRID_RETRY_COUNT.name, "2");

                    //TODO - this should be changed for local execution of Selenium tests on Chrome
                    setProperty(ApplicationProperty.CHROME_BINARY_PATH.name, "");
                }
            });
            put("local", new Properties() {
                {
                    setProperty(ApplicationProperty.APP_URL.name, "http://localhost:8080/#");
                    setProperty(ApplicationProperty.SERVICE_URL.name, "http://localhost:8080");
                    setProperty(ApplicationProperty.REST_CLIENT_READ_TIMEOUT.name, "60");
                    setProperty(ApplicationProperty.UPLOAD_SERVICE_TIMEOUT.name, "90");
                    setProperty(ApplicationProperty.RATING_STATUS_UPDATE_SERVICE_TIMEOUT.name, "90");
                }
            });

        }

    };

    private static String getString(String propertyName) {
        String currentEnv = System.getProperties().getProperty(
                APP_ENV.name, System.getenv(APP_ENV.name.toUpperCase().replace('.', '_')));

        if (System.getProperties().containsKey(propertyName)) {
            return System.getProperties().getProperty(propertyName);
        }
        if (currentEnv != null) {
            if (DEFAULT_VALUES.get(currentEnv).containsKey(propertyName)) {
                return DEFAULT_VALUES.get(currentEnv).getProperty(propertyName);
            }
        }
        if (DEFAULT_VALUES.get("default").containsKey(propertyName)) {
            return DEFAULT_VALUES.get("default").getProperty(propertyName);
        }

        log.warn("Unknown application property: " + propertyName);
        throw new RuntimeException("Unknown application property: " + propertyName);
    }

    public static String getString(ApplicationProperty property) {
        return getString(property.name);
    }

    public static Integer getInteger(ApplicationProperty property) {
        return Integer.valueOf(getString(property));
    }


    public static boolean getBoolean(ApplicationProperty property) {
        return Boolean.valueOf(getString(property));
    }

    public enum ApplicationProperty {

        APP_ENV("application.env"), APP_URL("application.appUrl"), SERVICE_URL("application.serviceUrl"), TARGET_BROWSER("application.targetBrowser"),
        WAIT_TIMEOUT_SHT("application.timeoutShort"), WAIT_TIMEOUT("application.timeoutRegular"), WAIT_TIMEOUT_LNG("application.timeoutLong"), WAIT_TIMEOUT_VERY_LNG("application.timeoutVeryLong"),
        REST_CLIENT_READ_TIMEOUT("application.restClientReadTimeout"),
        UPLOAD_SERVICE_TIMEOUT("application.uploadServiceTimeout"),
        RATING_STATUS_UPDATE_SERVICE_TIMEOUT("application.ratingStatusUpdateServiceTimeout"),
        USER("application.user"),

        REST_PROXY_ENABLED("proxy.restProxyEnabled"), BROWSER_PROXY_ENABLED("proxy.browserProxyEnabled"),
        PROXY_HOST("proxy.proxyHost"), PROXY_PORT("proxy.proxyPort"),
        REMOTE_DRIVER("selenium.remoteDriver"),
        SELENIUM_GRID_VIDEO_CAPTURE_URL("selenium.videoUrl"),
        SELENIUM_GRID_VIDEO_CAPTURE_ENABLED("selenium.videoCapture"),
        SELENIUM_GRID_RETRY_COUNT("selenium.seleniumGridRetries"),
        SELENIUM_GRID_URL("selenium.seleniumGridURL"),
        DESIRED_BROWSER_VERSION("selenium.desiredBrowserVersion"),
        DESIRED_PLATFORM("selenium.desiredPlatform"),
        CHROME_BINARY_PATH("chrome.binary.path");

        private String name;

        ApplicationProperty(String name) {
            this.name = name;
        }
    }
}
