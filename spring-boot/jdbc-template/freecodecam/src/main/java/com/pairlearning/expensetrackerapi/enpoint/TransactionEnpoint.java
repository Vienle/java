package com.pairlearning.expensetrackerapi.enpoint;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.entities.Transaction;
import com.pairlearning.expensetrackerapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories/{categoryId}/transactions")
public class TransactionEnpoint {
    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/{transactionId}")
    public ResponseEntity<Object> getTransactionById(HttpServletRequest request, @PathVariable(name = "categoryId") Integer categoryId,@PathVariable(name = "transactionId") Integer transactionId){
        int userId = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.featchTransactionById(userId,categoryId,transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody Map<String,Object> stringObjectMap){
        int userId = (Integer) request.getAttribute("userId");
        Double amount = Double.valueOf(stringObjectMap.get("amount").toString());
        String note = (String) stringObjectMap.get("note");
        Long transactionDte = (Long) stringObjectMap.get("transactionDate");
        Transaction transaction = transactionService.addTransaction(userId,categoryId,amount,note,transactionDte);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
