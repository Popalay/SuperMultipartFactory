package com.github.popalay.sample;

import com.google.gson.annotations.SerializedName;

public class NestedModel {

    @SerializedName("amount")
    private double amount;

    @SerializedName("currency_symbol")
    private String currencySymbol;

    public NestedModel() {
    }

    public NestedModel(double amount, String currencySymbol) {
        this.amount = amount;
        this.currencySymbol = currencySymbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
