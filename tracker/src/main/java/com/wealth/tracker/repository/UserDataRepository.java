package com.wealth.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth.tracker.model.UserData;

public interface UserDataRepository extends JpaRepository<UserData, String> {
    Optional<UserData> findByAadhaarNumber(String aadhaarNumber);
    Optional<UserData> findByPanNumber(String panNumber);
    Optional<UserData> findByAadhaarNumberAndPanNumber(String aadhaar, String pan);
}