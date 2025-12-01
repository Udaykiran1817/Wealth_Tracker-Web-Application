package com.wealth.tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "asset_data")
public class AssetData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ PRIMARY KEY (this was missing)

    @Column(name = "pan_number", nullable = false)
    private String panNumber;

    private String mutualFunds;
    private String investements;
    private String insurance;
    private String shares;

    @Column(name = "fixed_deposits")
    private String fixedDeposits;

    // ---------- GETTERS & SETTERS ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getMutualFunds() {
        return mutualFunds;
    }

    public void setMutualFunds(String mutualFunds) {
        this.mutualFunds = mutualFunds;
    }

    public String getInvestments() {
        return investements;
    }

    public void setInvestments(String investments) {
        this.investements = investments;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getFixedDeposits() {
        return fixedDeposits;
    }

    public void setFixedDeposits(String fixedDeposits) {
        this.fixedDeposits = fixedDeposits;
    }
}
