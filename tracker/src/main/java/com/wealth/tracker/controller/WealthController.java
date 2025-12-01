package com.wealth.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wealth.tracker.model.AssetData;
import com.wealth.tracker.model.DashboardData;
import com.wealth.tracker.model.LiabilityData;
import com.wealth.tracker.model.WealthSummaryData;
import com.wealth.tracker.repository.AssetDataRepository;
import com.wealth.tracker.repository.LiabilityDataRepository;
import com.wealth.tracker.service.WealthService;

@RestController
@RequestMapping("/wealth")
public class WealthController {

    @Autowired
private AssetDataRepository assetRepository;

@Autowired
private LiabilityDataRepository liabilityRepository;


    private final WealthService wealthService;

    public WealthController(WealthService wealthService) {
        this.wealthService = wealthService;
    }

    @GetMapping("/summary")
    public ResponseEntity<WealthSummaryData> getSummary(@RequestParam String pan) {
        return ResponseEntity.ok(wealthService.getSummaryByPan(pan));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardData> getDashboard(@RequestParam String pan) {
        return ResponseEntity.ok(wealthService.getFullDashboardByPan(pan));
    }

   // ✅ ADD ASSET
    @PostMapping("/assets")
    public ResponseEntity<?> addAsset(@RequestBody AssetData asset) {
        assetRepository.save(asset);
        return ResponseEntity.ok("Asset added successfully");
    }

    // ✅ ADD LIABILITY
    @PostMapping("/liabilities")
    public ResponseEntity<?> addLiability(@RequestBody LiabilityData liability) {
        liabilityRepository.save(liability);
        return ResponseEntity.ok("Liability added successfully");
}

@PostMapping("/assets/update/{id}")
public AssetData updateAsset(@PathVariable Long id, @RequestBody AssetData updated) {
    AssetData asset = assetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asset not found"));

    asset.setMutualFunds(updated.getMutualFunds());
    asset.setInvestments(updated.getInvestments());
    asset.setInsurance(updated.getInsurance());
    asset.setShares(updated.getShares());
    asset.setFixedDeposits(updated.getFixedDeposits());

    return assetRepository.save(asset);
}

@DeleteMapping("/assets/delete/{id}")
public void deleteAsset(@PathVariable Long id) {
    assetRepository.deleteById(id);
}

@PostMapping("/liabilities/update/{id}")
public LiabilityData updateLiability(@PathVariable Long id, @RequestBody LiabilityData updated) {
    LiabilityData liability = liabilityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Liability not found"));

    liability.setHomeLoan(updated.getHomeLoan());
    liability.setEmi(updated.getEmi());
    liability.setPersonalLoans(updated.getPersonalLoans());

    return liabilityRepository.save(liability);
}

@DeleteMapping("/liabilities/delete/{id}")
public void deleteLiability(@PathVariable Long id) {
    liabilityRepository.deleteById(id);
}



}
