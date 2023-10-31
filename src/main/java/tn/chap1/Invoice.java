package tn.chap1;

import java.util.List;

public class Invoice {
    private String customer;
    private List<Performance> performances;

    // Constructors, getters, and setters

    // Constructor
    public Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    // Getters and setters
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
