package com.companyname.at.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final int WAIT_SMALL = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_TIMEOUT_SHT);
    public static final int WAIT_NORMAL = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_TIMEOUT);
    public static final int WAIT_LONG = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_TIMEOUT_LNG);
    public static final int WAIT_VERY_LONG = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_TIMEOUT_VERY_LNG);
    public static final int REST_CLIENT_READ_TIMEOUT = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.REST_CLIENT_READ_TIMEOUT);
}