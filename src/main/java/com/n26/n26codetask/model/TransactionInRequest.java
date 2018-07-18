package com.n26.n26codetask.model;

import java.io.Serializable;

public class TransactionInRequest implements Serializable {
    private double amount;
    private long timestamp;

    public TransactionInRequest(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public TransactionInRequest() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
