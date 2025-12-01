package com.wealth.tracker.model;

import java.util.List;

public class WealthSummaryData {

    private List<AssetData> assets;
    private List<LiabilityData> liabilities;

    public List<AssetData> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetData> assets) {
        this.assets = assets;
    }

    public List<LiabilityData> getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(List<LiabilityData> liabilities) {
        this.liabilities = liabilities;
    }
}
