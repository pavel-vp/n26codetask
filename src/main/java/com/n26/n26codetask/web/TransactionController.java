package com.n26.n26codetask.web;

import com.n26.n26codetask.model.StatisticResponse;
import com.n26.n26codetask.model.TransactionInRequest;
import com.n26.n26codetask.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity doTransaction(@RequestBody TransactionInRequest transactionInRequest) {

        boolean isNew = transactionService.addTransaction(transactionInRequest);
        if (isNew) {
            return ResponseEntity.status(201).body(null);
        } else {
            return ResponseEntity.status(204).body(null);
        }
    }


    @GetMapping("/statistics")
    public @ResponseBody StatisticResponse getStatistics() {
        return transactionService.getStatistics();
    }




}
