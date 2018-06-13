package com.companyname.at.support.ui;

import com.companyname.at.config.ApplicationProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.companyname.at.config.ApplicationProperties.ApplicationProperty.SELENIUM_GRID_VIDEO_CAPTURE_URL;


public class WebDriverUtils {

    public static String getVideoUrlFromGrid(WebDriver webdriver) {
        String sessionId = ((RemoteWebDriver) webdriver).getSessionId().toString();
        return ApplicationProperties.getString(SELENIUM_GRID_VIDEO_CAPTURE_URL) + sessionId;
    }
}