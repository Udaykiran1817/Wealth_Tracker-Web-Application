package com.wealth.tracker.util;

import java.security.SecureRandom;

public class OtpGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp(int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        int value = random.nextInt(max - min + 1) + min;
        return String.valueOf(value);
    }
}

