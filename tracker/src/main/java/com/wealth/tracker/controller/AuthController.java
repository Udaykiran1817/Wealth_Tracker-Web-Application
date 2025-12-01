package com.wealth.tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wealth.tracker.model.AuthDtos.AadhaarRequest;
import com.wealth.tracker.model.AuthDtos.ApiResponse;
import com.wealth.tracker.model.AuthDtos.OtpRequest;
import com.wealth.tracker.model.AuthDtos.PanRequest;
import com.wealth.tracker.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/verify-aadhaar")
    public ResponseEntity<ApiResponse<Void>> verifyAadhaar(@RequestBody AadhaarRequest req) {
    try {
        authService.verifyAadhaar(req.aadhaarNumber());
        return ResponseEntity.ok(new ApiResponse<>(true, "Aadhaar verified", null));
    } catch (RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}


@PostMapping("/verify-pan")
public ResponseEntity<ApiResponse<Void>> verifyPan(@RequestBody PanRequest req) {
    try {
        authService.verifyPanForAadhaar(req.aadhaarNumber(), req.panNumber());
        return ResponseEntity.ok(new ApiResponse<>(true, "PAN verified", null));
    }
    catch (RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}


    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestBody PanRequest req) {
        String masked = authService.sendOtp(req.aadhaarNumber(), req.panNumber());
        String msg = "SMS sent to registered number ending " + masked.substring(masked.length()-4);
        return ResponseEntity.ok(new ApiResponse<>(true, msg, masked));
    }

    @PostMapping("/verify-otp")
public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody OtpRequest req) {
    try {
        String token = authService.verifyOtp(
                req.aadhaarNumber(),
                req.panNumber(),
                req.otp()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "OTP verified", token));
    } catch (RuntimeException ex) {
        // 401 instead of 500, with clear message
        return ResponseEntity
                .status(401)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}
}
