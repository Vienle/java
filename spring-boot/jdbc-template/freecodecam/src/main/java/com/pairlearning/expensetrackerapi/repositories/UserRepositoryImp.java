package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.entities.User;
import com.pairlearning.expensetrackerapi.exceptions.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImp implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL,PASSWORD) VALUES" +
            "(? , ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*)FROM ET_USERS WHERE EMAIL = ? ";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE USER_ID = ? ";
    private static final String SQL_LAST_USER_ID = "SELECT USER_ID from ET_USERS ORDER BY USER_ID DESC LIMIT 1 ";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM ET_USERS WHERE EMAIL = ? ";


    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        try {

            Integer user_id = jdbcTemplate.queryForObject(SQL_LAST_USER_ID,userID);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps =  connection.prepareStatement(SQL_CREATE);
                ps.setInt(1,user_id + 1);
                ps.setString(2,firstName);
                ps.setString(3,lastName);
                ps.setString(4,email);
                ps.setString(5,password);
                return ps;
            });
           return user_id + 1;
        }catch (Exception ex){
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            if (!password.equals(user.getPassword())){
                throw new EtAuthException("Invalid email/password");
            }
            return user;
        }catch (Exception ex){
            throw new EtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId},userRowMapper);
    }
    private RowMapper<User> userRowMapper = ((rs, rowNum) ->{
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
    private RowMapper<Integer> userID = ((resultSet, i) -> new Integer(resultSet.getInt("USER_ID")));
}
