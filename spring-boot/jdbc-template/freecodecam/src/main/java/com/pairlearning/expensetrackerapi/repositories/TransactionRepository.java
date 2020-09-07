package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.entities.Transaction;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionId);

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    Transaction findById(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void romoveById(Integer userID, Integer categoryId, Integer transactionId) throws EtBadRequestException;
}
