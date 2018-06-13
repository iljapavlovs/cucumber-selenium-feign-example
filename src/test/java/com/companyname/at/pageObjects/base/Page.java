package com.companyname.at.pageObjects.base;

import com.companyname.at.config.Constants;
import com.companyname.at.webdriver.DriverBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page() {
        this.driver = DriverBase.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Constants.WAIT_SMALL);
    }
}
