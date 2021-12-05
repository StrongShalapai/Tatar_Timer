package com.example.tatar_timer.sampledata;

//HLP means Help
public class HLP_Class {

    public static String buildStringFromArray(String[] array, String DELIMITER) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s);
            builder.append(DELIMITER);
        }
        return builder.toString();
    }


}
