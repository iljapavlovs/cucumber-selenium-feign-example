package com.companyname.at.support.ui;

import com.companyname.at.webdriver.DriverBase;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.Callable;

import static com.companyname.at.config.Constants.WAIT_SMALL;
import static org.junit.Assert.fail;

@Slf4j
public class WebElementHelper {

    public static boolean isElementDisplayed(WebElement webElement) {
        return isElementDisplayed(webElement, WAIT_SMALL);
    }

    public static boolean isElementDisplayed(WebElement webElement, int timeOut) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), timeOut);
            webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
            return webElement.isDisplayed();
        } catch (NoSuchElementException | TimeoutException te) {
            log.info("Element is not displayed with exception: " + te.getMessage());
            return false;
        } catch (StaleElementReferenceException ex) {
            return isElementDisplayed(webElement, timeOut);
        }
    }


    public static void waitForVisibility(WebElement element) {
        WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), WAIT_SMALL);
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException te) {
            log.error(te.getMessage());
            fail("Element '" + element + "' not found after waiting for it's visibility");
        } catch (NoSuchElementException ne) {
            log.error(ne.getMessage());
            fail("Element '" + element + "' not found, unable to locate it");
        } catch (Exception e) {
            log.error(e.getMessage());
            fail("Element '" + element + "' not found");
        }
    }

    public static void sendKeys(WebElement webElement, CharSequence... text) {
        log.info("Setting textbox value - " + text);
        WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), WAIT_SMALL);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        webElement.sendKeys(text);
    }

    public static void click(WebElement webElement) {
        if (isElementDisplayed(webElement)) {
            waitForElementToBeClickable(webElement);
            webElement.click();
        } else {
            throw new RuntimeException("Can't click on the element");
        }
    }


    public static void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), WAIT_SMALL);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));

    }

    public static String getText(WebElement webElement) {
        waitForVisibility(webElement);
        return webElement.getText();
    }


    public static String getValue(WebElement webElement) {
        waitForVisibility(webElement);
        return webElement.getAttribute("value");
    }

    public static void navigateToPage(String url) {
        log.info("Navigating to: " + url);
        DriverBase.getDriver().get(url);
    }


    public static void scrollToCenterOfScreen(WebElement webElement) {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) DriverBase.getDriver()).executeScript(scrollElementIntoMiddle, webElement);
    }


    private static Callable<Boolean> itemAppeared(WebDriver driver, By elementLocator) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return driver.findElements(elementLocator).size() != 0;
            }
        };
    }


    public static void setColorForDebug(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) DriverBase.getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background-color:coral')", element);
    }

    public static void clickWithOffset(WebElement element) {
        Actions builder = new Actions(DriverBase.getDriver());
        Action action = builder.moveToElement(element, 2, 2).click().build();
        action.perform();
    }

    public static void waitForCookie(String cookieName) {
        new WebDriverWait(DriverBase.getDriver(), WAIT_SMALL).until(d -> d.manage().getCookieNamed(cookieName) != null);
    }

    public static boolean isElementPresentByLocator(By locator) {
        return DriverBase.getDriver().findElements(locator).size() != 0;
    }

    public static void waitUntilElementToDisappear(By by, int timeout) {
        WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), timeout);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) DriverBase.getDriver()).executeScript("arguments[0].scrollIntoView(true)", webElement);
    }
}

