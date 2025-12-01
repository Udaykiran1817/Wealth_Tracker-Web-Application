package com.wealth.tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wealth.tracker.model.UserData;
import com.wealth.tracker.repository.UserDataRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDataRepository userRepo;

    public UserController(UserDataRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/by-pan")
    public UserData getByPan(@RequestParam String pan) {
        return userRepo.findByPanNumber(pan)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
