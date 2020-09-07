package com.pairlearning.expensetrackerapi.services;

import com.pairlearning.expensetrackerapi.entities.Transaction;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;
import com.pairlearning.expensetrackerapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
        return null;
    }

    @Override
    public Transaction featchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException {
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int transactionId = (Integer) transactionRepository.create(userId,categoryId,amount,note,transactionDate);
        return transactionRepository.findById(userId,categoryId,transactionId);
    }


    @Override
    public Transaction updateTransactionById(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        return null;
    }

    @Override
    public Transaction removeTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException {
        return null;
    }
}
