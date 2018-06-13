package com.companyname.at.support;

import com.companyname.at.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.companyname.at.config.ApplicationProperties.ApplicationProperty.valueOf;

@Slf4j
public class StringUtil {
    private static TestDataContext testData = TestDataContext.getInstance();

    public static String replaceDynamicValuesInText(String text) {
        text = findAndReplaceContextVariables(text);
        text = findAndReplaceUserVariables(text);
        return text;
    }

    private static String findAndReplaceUserVariables(String text) {
        // Replace users in sceanrios - e.g. $U{USER}
        Pattern p = Pattern.compile("\\$U\\{(\\S+)\\}");
        Matcher m = p.matcher(text);
        while (m.find()) {
            // 0 group - whole string; 1st group - user part
            text = text.replace(m.group(0), ApplicationProperties.getString(ApplicationProperties.ApplicationProperty.valueOf(m.group(1))));
        }
        //returns the end user name from App Properties
        return text;
    }

    private static String findAndReplaceContextVariables(String text) {
        if (text != null) {
            Pattern p = Pattern.compile("\\$\\{(\\S+)\\}");
            Matcher m = p.matcher(text);
            while (m.find()) {
                String value = m.group(0);
                String key = m.group(1);
                text = replaceTextVariable(text, value, key);
            }
        }
        return text;
    }

    private static String replaceTextVariable(String text, String textToReplace, String key) {
        if (testData.getTestDataMap().containsKey(key)) {
            String value = testData.getTestDataMap().get(key).get();
            log.debug("Text replace " + key + "=" + value);
            return text.replace(textToReplace, value);
        } else {
            throw new IllegalArgumentException("Unknown key -  " + textToReplace);
        }
    }

    public static String normalizeJsonString(String jsonString) {
        String result = jsonString.trim();
        return result.replaceAll("\\n", "")
                .replaceAll("\\s+", " ")
                .replaceAll("\\r", "");
    }

    public static String generateIntegerUuid(){
        UUID uuid = UUID.randomUUID();
        int hi = (int) uuid.getMostSignificantBits();
        int lo = (int) uuid.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
        BigInteger big = new BigInteger(bytes);
        return big.toString().replace('-','1');
    }
}