package com.wealth.tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwoFactorSmsService {

    @Value("${twofactor.api-key}")
    private String apiKey;

    public void sendOtp(String phone, String otp) {

        // 2Factor needs number WITHOUT +91
        if (phone.startsWith("+91")) {
            phone = phone.substring(3);
        }

        String url = "https://2factor.in/API/V1/" + apiKey +
                     "/SMS/" + phone +
                     "/" + otp +
                     "/OTP1";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }
}

