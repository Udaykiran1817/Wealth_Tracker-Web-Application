package com.wealth.tracker.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_data")
public class UserData {

    @Id
    @Column(name = "aadhaar_number", length = 12)
    private String aadhaarNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "pan_number", nullable = false, unique = true, length = 10)
    private String panNumber;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

  public String getAadhaarNumber() {
    return this.aadhaarNumber;
  }

  public void setAadhaarNumber(String aadhaarNumber) {
    this.aadhaarNumber = aadhaarNumber;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPanNumber() {
    return this.panNumber;
  }

  public void setPanNumber(String panNumber) {
    this.panNumber = panNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

    // getters & setters

    public String getPhoneNumber() {
      System.out.println("Your Phone Number is: " + phoneNumber);
      return phoneNumber;
    }
}
