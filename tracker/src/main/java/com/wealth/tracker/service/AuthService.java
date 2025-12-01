package com.wealth.tracker.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wealth.tracker.model.UserData;
import com.wealth.tracker.repository.UserDataRepository;
import com.wealth.tracker.util.JwtUtil;
import com.wealth.tracker.util.OtpGenerator;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserDataRepository userRepo;
    private final OtpService otpService;
    private final TwoFactorSmsService twoFactorSmsService;

    public AuthService(UserDataRepository userRepo,
                       OtpService otpService,
                       TwoFactorSmsService twoFactorSmsService) {
        this.userRepo = userRepo;
        this.otpService = otpService;
        this.twoFactorSmsService = twoFactorSmsService;
    }

    public UserData verifyAadhaar(String aadhaar) {
        if (aadhaar == null || !aadhaar.matches("\\d{12}")) {
            throw new IllegalArgumentException("Aadhaar must be 12 digits");
        }
        return userRepo.findByAadhaarNumber(aadhaar)
                .orElseThrow(() -> new RuntimeException("Invalid Aadhaar number"));
    }

    public UserData verifyPanForAadhaar(String aadhaar, String pan) {
        String normAadhaar = aadhaar == null ? null : aadhaar.trim();
        String normPan = pan == null ? null : pan.trim().toUpperCase();

        if (!StringUtils.hasText(normPan)) {
            throw new RuntimeException("PAN cannot be empty");
        }

        return userRepo.findByAadhaarNumberAndPanNumber(normAadhaar, normPan)
                .orElseThrow(() -> new RuntimeException("PAN does not match this Aadhaar"));
    }

    // ---------- SEND OTP ----------

    public String sendOtp(String aadhaar, String pan) {

        String normAadhaar = aadhaar.trim();
        String normPan = pan.trim().toUpperCase();

        UserData user = verifyPanForAadhaar(normAadhaar, normPan);

        String otp = OtpGenerator.generateOtp(6);
        String key = normAadhaar + "|" + normPan;

        // store for 5 minutes
        otpService.saveOtp(key, otp, 5 * 60 * 1000);

        twoFactorSmsService.sendOtp(user.getPhoneNumber(), otp);

        System.out.println("==== OTP SENT ====");
        System.out.println("KEY  : " + key);
        System.out.println("OTP  : " + otp);

        String phone = user.getPhoneNumber();
        String last4 = phone.substring(Math.max(phone.length() - 4, 0));
        return "****" + last4;
    }

    // ---------- VERIFY OTP ----------

    public String verifyOtp(String aadhaar, String pan, String otp) {

        String normAadhaar = aadhaar == null ? null : aadhaar.trim();
        String normPan = pan == null ? null : pan.trim().toUpperCase();
        String normOtp = otp == null ? null : otp.trim();

        System.out.println("==== VERIFY OTP REQUEST ====");
        System.out.println("AADHAAR : " + normAadhaar);
        System.out.println("PAN     : " + normPan);
        System.out.println("OTP     : " + normOtp);

        if (!StringUtils.hasText(normAadhaar) ||
            !StringUtils.hasText(normPan) ||
            !StringUtils.hasText(normOtp)) {
            throw new RuntimeException("Missing aadhaar / pan / otp");
        }

        String key = normAadhaar + "|" + normPan;
        System.out.println("VERIFY KEY : " + key);

        boolean valid = otpService.validateOtp(key, normOtp);

        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        otpService.clearOtp(key);

        Map<String, Object> claims = new HashMap<>();
        claims.put("aadhaar", normAadhaar);

        // subject = PAN
        return jwtUtil.generateToken(normPan, claims);
    }
}
