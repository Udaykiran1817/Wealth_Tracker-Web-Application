package com.wealth.tracker.model;

public class AuthDtos {

    public record AadhaarRequest(String aadhaarNumber) {}
    public record PanRequest(String aadhaarNumber, String panNumber) {}
    public record OtpRequest(String aadhaarNumber, String panNumber, String otp) {}

    public record ApiResponse<T>(boolean success, String message, T data) {}
}
