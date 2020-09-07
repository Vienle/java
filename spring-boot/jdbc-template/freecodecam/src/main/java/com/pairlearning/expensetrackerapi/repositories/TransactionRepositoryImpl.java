package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.entities.Transaction;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;
import com.pairlearning.expensetrackerapi.exceptions.EtResouceNotFoundException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ? ";
    private static final String SQL_CREATE_TRANSACTION = "INSERT INTO ET_TRANSACTIONS(TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE) VALUES ( ?, ? , ?, ?, ?, ?)";
    private static final String SQL_LAST_TRANSACTION_ID = "SELECT TRANSACTION_ID from ET_TRANSACTIONS ORDER BY TRANSACTION_ID DESC LIMIT 1";
    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return null;
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId,transactionId},transactionRowMapper);
        }catch (Exception ex){
            throw new EtResouceNotFoundException(ex.getMessage());
//            throw new EtResouceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        try {
            Integer transactionId = jdbcTemplate.queryForObject(SQL_LAST_TRANSACTION_ID,getIdTran);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_TRANSACTION);
                ps.setInt(1,transactionId + 1);
                ps.setInt(2,categoryId);
                ps.setInt(3,userId);
                ps.setDouble(4, amount);
                ps.setString(5,note);
                ps.setLong(6,transactionDate);
                return ps;
            });
            return transactionId + 1;
        }catch (Exception ex){
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        return null;
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {

    }

    @Override
    public void romoveById(Integer userID, Integer categoryId, Integer transactionId) throws EtBadRequestException {

    }

    private RowMapper<Transaction> transactionRowMapper = ((resultSet, i) -> {
        return new Transaction(
                resultSet.getInt("TRANSACTION_ID"),
                resultSet.getInt("CATEGORY_ID"),
                resultSet.getInt("USER_ID"),
                resultSet.getDouble("AMOUNT"),
                resultSet.getString("NOTE"),
                resultSet.getLong("TRANSACTION_DATE")
        );
    });
    private RowMapper<Integer> getIdTran = ((resultSet, i) -> new Integer(resultSet.getInt("TRANSACTION_ID")));
}
