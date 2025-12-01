package com.wealth.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wealth.tracker.model.AssetData;
import com.wealth.tracker.model.DashboardData;
import com.wealth.tracker.model.LiabilityData;
import com.wealth.tracker.model.WealthSummaryData;
import com.wealth.tracker.repository.AssetDataRepository;
import com.wealth.tracker.repository.LiabilityDataRepository;

@Service
public class WealthService {

    private final AssetDataRepository assetRepository;
    private final LiabilityDataRepository liabilityRepository;

    public WealthService(AssetDataRepository assetRepository,
                         LiabilityDataRepository liabilityRepository) {
        this.assetRepository = assetRepository;
        this.liabilityRepository = liabilityRepository;
    }

    // ✅ SUMMARY DATA (if you still use it)
    public WealthSummaryData getSummaryByPan(String pan) {
        List<AssetData> assets = assetRepository.findByPanNumber(pan);
        List<LiabilityData> liabilities = liabilityRepository.findByPanNumber(pan);

        WealthSummaryData summary = new WealthSummaryData();
        summary.setAssets(assets);
        summary.setLiabilities(liabilities);

        return summary;
    }

    // ✅ FULL DASHBOARD DATA
    public DashboardData getFullDashboardByPan(String pan) {
        List<AssetData> assets = assetRepository.findByPanNumber(pan);
        List<LiabilityData> liabilities = liabilityRepository.findByPanNumber(pan);

        DashboardData dashboard = new DashboardData();
        dashboard.setAssets(assets);
        dashboard.setLiabilities(liabilities);

        return dashboard;
    }

    // ✅ NEW: Add Asset for a PAN
    public AssetData addAsset(AssetData asset) {
        return assetRepository.save(asset);
    }

    // ✅ NEW: Add Liability for a PAN
    public LiabilityData addLiability(LiabilityData liability) {
        return liabilityRepository.save(liability);
    }
}
