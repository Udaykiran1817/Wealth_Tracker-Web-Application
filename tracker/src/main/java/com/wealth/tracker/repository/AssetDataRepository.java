package com.wealth.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth.tracker.model.AssetData;

public interface AssetDataRepository extends JpaRepository<AssetData, Long> {
    List<AssetData> findByPanNumber(String panNumber);

    void deleteById(Long id);

}