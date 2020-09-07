package com.pairlearning.expensetrackerapi.services;

import com.pairlearning.expensetrackerapi.entities.Transaction;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;

import java.util.List;

public interface TransactionService {
    List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

    Transaction featchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException;

    Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    Transaction updateTransactionById(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    Transaction removeTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException;
}
