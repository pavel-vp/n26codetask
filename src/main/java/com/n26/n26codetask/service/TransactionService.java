package com.n26.n26codetask.service;

import com.n26.n26codetask.model.DataNode;
import com.n26.n26codetask.model.StatisticResponse;
import com.n26.n26codetask.model.TransactionInRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TransactionService {
    private DataNode firstNode;


    public boolean addTransaction(TransactionInRequest transactionInRequest) {
        long deadline = Calendar.getInstance().getTimeInMillis() - 60 * 1000;
        truncateOldNodes(deadline);
        addNode(transactionInRequest);
        return transactionInRequest.getTimestamp() >= deadline;
    }

    private void truncateOldNodes(long deadline) {
        // truncate old nodes older than 60 secs
        if (firstNode != null) {
            DataNode node = firstNode;
            while (node != null) {
                if (node.getTimestamp() < deadline) {
                    firstNode = node.getNextNode();
                    if (firstNode != null) {
                        firstNode.setPrevNode(null);
                    }
                }
                node = node.getNextNode();
            }
        }
    }

    private void addNode(TransactionInRequest transactionInRequest) {
        // find right place to add
        DataNode nodeAfter = findNode(transactionInRequest.getTimestamp());
        if (nodeAfter == null) {
            firstNode = new DataNode();
            firstNode.setAmount(transactionInRequest.getAmount());
            firstNode.setTimestamp(transactionInRequest.getTimestamp());
        } else {
            DataNode newNode = new DataNode();
            newNode.setAmount(transactionInRequest.getAmount());
            newNode.setTimestamp(transactionInRequest.getTimestamp());
            newNode.setNextNode(nodeAfter.getNextNode());
            newNode.setPrevNode(nodeAfter);
            nodeAfter.setNextNode(newNode);
        }
    }

    private DataNode findNode(long timestamp) {
        if (firstNode == null) {
            return null;
        }
        DataNode node = firstNode;
        while (node != null) {
            if (node.getTimestamp() < timestamp) {
                return node;
            }
            if (node.getNextNode() == null) {
                return node;
            }
            node = node.getNextNode();
        }
        return null;
    }

    public StatisticResponse getStatistics() {
        long deadline = Calendar.getInstance().getTimeInMillis() - 60 * 1000;
        StatisticResponse statisticResponse = new StatisticResponse();
        DataNode node = firstNode;
        while (node != null) {
            if (node.getTimestamp() >= deadline) {
                // calculate stats
                calculateStat(statisticResponse, node);
            }
            node = node.getNextNode();
        }
        return statisticResponse;
    }

    private void calculateStat(StatisticResponse statisticResponse, DataNode node) {
        statisticResponse.setCount(statisticResponse.getCount() + 1);
        statisticResponse.setSum(statisticResponse.getSum() + node.getAmount());
        statisticResponse.setAvg(statisticResponse.getSum() / statisticResponse.getCount());
        if (statisticResponse.getMin() == 0) {
            statisticResponse.setMin(node.getAmount());
        } else {
            statisticResponse.setMin(Math.min(statisticResponse.getMin(), node.getAmount()));
        }
        statisticResponse.setMax(Math.max(statisticResponse.getMax(), node.getAmount()));
    }
}
