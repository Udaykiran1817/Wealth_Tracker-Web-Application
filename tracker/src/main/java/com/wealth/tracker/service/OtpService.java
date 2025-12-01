package com.wealth.tracker.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

    // Internal structure with OTP + expiry
    private static class OtpData {
        String otp;
        long expiry;
    }

    // key -> otpData
    private final Map<String, OtpData> store = new ConcurrentHashMap<>();

    // Save OTP with expiry
    public void saveOtp(String key, String otp, long expiryMillis) {
        if (key == null || key.isBlank() || otp == null || otp.isBlank()) {
            throw new IllegalArgumentException("OTP key / value cannot be empty");
        }

        OtpData data = new OtpData();
        data.otp = otp;
        data.expiry = System.currentTimeMillis() + expiryMillis;
        store.put(key, data);

        System.out.println("==== OTP SAVED ====");
        System.out.println("KEY   : " + key);
        System.out.println("OTP   : " + otp);
        System.out.println("EXPIRES AT : " + data.expiry);
    }

    // Validate OTP ⇒ returns true / false (NO exceptions here)
    public boolean validateOtp(String key, String otp) {
        System.out.println("==== OTP VALIDATION ====");
        System.out.println("REQ KEY : " + key);
        System.out.println("REQ OTP : " + otp);

        if (key == null || otp == null) {
            System.out.println("KEY / OTP null");
            return false;
        }

        OtpData data = store.get(key);

        if (data == null) {
            System.out.println("NO OTP STORED FOR KEY");
            return false;
        }

        long now = System.currentTimeMillis();
        if (now > data.expiry) {
            System.out.println("OTP EXPIRED");
            store.remove(key);
            return false;
        }

        System.out.println("STORED OTP : " + data.otp);
        boolean match = data.otp.equals(otp);
        System.out.println("MATCH? " + match);
        return match;
    }

    public void clearOtp(String key) {
        store.remove(key);
        System.out.println("OTP CLEARED FOR KEY: " + key);
    }
}
