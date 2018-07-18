package com.n26.n26codetask.model;

public class DataNode {
    private double amount;
    private long timestamp;

    private DataNode nextNode;
    private DataNode prevNode;

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

    public DataNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(DataNode nextNode) {
        this.nextNode = nextNode;
    }

    public DataNode getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(DataNode prevNode) {
        this.prevNode = prevNode;
    }
}
