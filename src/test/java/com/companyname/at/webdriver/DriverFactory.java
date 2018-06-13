package com.companyname.at.webdriver;

import com.companyname.at.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

@Slf4j
public class DriverFactory {

    private final DriverType defaultDriverType = DriverType.valueOf(ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.TARGET_BROWSER).toUpperCase());
    private final String browser = System.getProperty("browser", defaultDriverType.toString());
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = ApplicationProperties.getBoolean(ApplicationProperties.ApplicationProperty.REMOTE_DRIVER);
    private final boolean proxyEnabled = ApplicationProperties.getBoolean(ApplicationProperties.ApplicationProperty.BROWSER_PROXY_ENABLED);
    private final String proxyHostname = ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.PROXY_HOST);
    private final Integer proxyPort = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.PROXY_PORT);
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

    private WebDriver webdriver;
    private DriverType selectedDriverType;

    public WebDriver getDriver() {
        if (null == webdriver) {
            Proxy proxy = null;
            if (proxyEnabled) {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }
            determineEffectiveDriverType();
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities(proxy);
            instantiateWebDriver(desiredCapabilities);
            webdriver.manage().window().maximize();
        }

        return webdriver;
    }

    void quitDriver() {
        if (null != webdriver) {
            webdriver.quit();
        }
    }

    private void determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            log.error("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            log.error("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) {
        log.info("Current Operating System: " + operatingSystem);
        log.info("Current Architecture: " + systemArchitecture);
        try {

            if (useRemoteWebDriver) {
                boolean videoCaptureEnabled = ApplicationProperties.getBoolean(ApplicationProperties.ApplicationProperty.SELENIUM_GRID_VIDEO_CAPTURE_ENABLED);

                desiredCapabilities.setCapability("video", videoCaptureEnabled); //for video recording on Selenium Grid node
                desiredCapabilities.setCapability("project", ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.PROJECT_NAME));                           // Project name for logging
                desiredCapabilities.setCapability("apm_id", ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.APM_ID));                                // Projects APM ID for logging

                String seleniumGridUrl = ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.SELENIUM_GRID_URL);
                log.info("Starting remote webdriver with Selenium Grid url - " + seleniumGridUrl);

                URL seleniumGridURL = new URL(seleniumGridUrl);
                String desiredBrowserVersion = ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.DESIRED_BROWSER_VERSION);
                String desiredPlatform = ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.DESIRED_PLATFORM);

                if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                    desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
                }

                if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                    desiredCapabilities.setVersion(desiredBrowserVersion);
                }
                webdriver = initRemoteWebdriver(seleniumGridURL, desiredCapabilities);
            } else {
                log.info("Current Browser Selection: " + selectedDriverType);

                webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
            }

        } catch (MalformedURLException e) {
            log.error("Error parsing Grid Url + " + e.getMessage());
        }
    }

    private WebDriver initRemoteWebdriver(URL seleniumGridURL, DesiredCapabilities desiredCapabilities) {
        RemoteWebDriver driver;
        int attemptNumber = 0;
        int maxTries = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.SELENIUM_GRID_RETRY_COUNT);

        while (true) {
            try {
                attemptNumber++;
                driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
                driver.setFileDetector(new LocalFileDetector());
                break;
            } catch (Exception e) {
                log.error(String.format("Remote webdriver init failed. Attempt # - %s. Exception - %s", String.valueOf(attemptNumber), e.getClass().getSimpleName()));
                if (attemptNumber == maxTries) throw e;
            }
        }
        return driver;
    }
}

