package com.companyname.at.support.ui;

import com.companyname.at.config.ApplicationProperties;

import static com.companyname.at.config.ApplicationProperties.ApplicationProperty.APP_URL;
import static com.companyname.at.support.ui.WebElementHelper.navigateToPage;

public class BrowserNavigation {

    public static void open() {
        navigateToPage(ApplicationProperties.getString(APP_URL));
    }

}