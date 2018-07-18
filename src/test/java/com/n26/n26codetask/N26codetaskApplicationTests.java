package com.n26.n26codetask;

import com.n26.n26codetask.model.StatisticResponse;
import com.n26.n26codetask.model.TransactionInRequest;
import com.n26.n26codetask.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

public class N26codetaskApplicationTests {


    TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    public void addAndCheck() {
        long timeNow = Calendar.getInstance().getTimeInMillis();
        for (int i = 1;i<=100;i++) {
            TransactionInRequest trIn = new TransactionInRequest(100* Math.random()+10, timeNow - i * 100);
            transactionService.addTransaction(trIn);
        }
        StatisticResponse statisticResponse = transactionService.getStatistics();
        Assert.assertTrue(statisticResponse.getMin() < statisticResponse.getMax());
        Assert.assertTrue(statisticResponse.getCount() == 100);
    }

}
