package com.companyname.at.pageObjects;


import com.companyname.at.pageObjects.base.Page;
import com.companyname.at.support.ui.WebElementHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class GooglePage extends Page {

    @FindBy(how = How.NAME, using = "q")
    private WebElement inputField;

    public GooglePage() {
        wait.until(ExpectedConditions.visibilityOf(inputField));
    }

    public static GooglePage navigate() {
        return new GooglePage();
    }

    public GoogleSearchResultPage searchFor(String textToSearchFor) {
        WebElementHelper.sendKeys(inputField, textToSearchFor, Keys.ENTER);
        return new GoogleSearchResultPage();
    }

}