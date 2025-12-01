package com.wealth.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth.tracker.model.LiabilityData;

public interface LiabilityDataRepository extends JpaRepository<LiabilityData, Long> {
    List<LiabilityData> findByPanNumber(String panNumber);
}