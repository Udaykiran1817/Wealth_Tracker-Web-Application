package com.wealth.tracker.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "liability_data")
public class LiabilityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pan_number", nullable = false, length = 10)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPanNumber() {
        return this.panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPersonalLoans() {
        return this.personalLoans;
    }

    public void setPersonalLoans(String personalLoans) {
        this.personalLoans = personalLoans;
    }

    public String getEmi() {
        return this.emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getHomeLoan() {
        return this.homeLoan;
    }

    public void setHomeLoan(String homeLoan) {
        this.homeLoan = homeLoan;
    }
    private String panNumber;
    private String personalLoans;
    private String emi;
    private String homeLoan;

    // getters & setters
}

