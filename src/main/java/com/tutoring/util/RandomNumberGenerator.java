package com.tutoring.util;

import java.util.Random;

/**
 * Created by Himanshu.
 */
public class RandomNumberGenerator {
    public static final Random r = new Random();
    public static long generateRandomNumber(){
        int low = 100000;
        int high = 999999;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    public static String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
