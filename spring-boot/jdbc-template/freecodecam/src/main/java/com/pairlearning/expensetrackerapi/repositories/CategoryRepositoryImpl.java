package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;
import com.pairlearning.expensetrackerapi.exceptions.EtResouceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_FIND_ALL = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID ";
    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID ";

    private static final String SQL_LAST_CATEGORY_ID = "SELECT CATEGORY_ID from ET_CATEGORIES ORDER BY CATEGORY_ID DESC LIMIT 1";
    private static final String SQL_CREATE_CATEGORY = "INSERT INTO ET_CATEGORIES(CATEGORY_ID, USER_ID, TITLE, DESCRIPTION ) VALUES ( ?, ?, ?, ? )";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE ET_CATEGORIES SET TITLE = ? ,DESCRIPTION = ? WHERE USER_ID = ? AND CATEGORY_ID = ? ";

    @Override
    public List<Category> findAll(Integer userId) throws EtResouceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL,new Object[]{userId},categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResouceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId},categoryRowMapper);
        }catch (Exception ex){
//            throw  new EtResouceNotFoundException(ex.getMessage());
            throw new EtResouceNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtResouceNotFoundException {
        try {
            Integer categoryId = (Integer) jdbcTemplate.queryForObject(SQL_LAST_CATEGORY_ID,categoryIdMapper);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CATEGORY);
                ps.setInt(1,categoryId + 1);
                ps.setInt(2,userId);
                ps.setString(3,title);
                ps.setString(4,description);
                return ps;
            });
            return categoryId + 1;
        }catch (Exception ex){
            ex.getMessage();
            throw new EtBadRequestException("Invalid Request");
        }

    }

    @Override
    public void update(int userId, int categoryId, Category category) throws EtResouceNotFoundException {
        try {
            jdbcTemplate.update(SQL_UPDATE_CATEGORY,new Object[]{category.getTitle(),category.getDescription(),userId,categoryId});
        }catch (Exception ex){
            throw  new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void remove(int userId, int categoryId) {

    }

    private RowMapper<Integer> categoryIdMapper = ((resultSet, i) -> new Integer(resultSet.getInt("CATEGORY_ID")));
    private RowMapper<Category> categoryRowMapper = ((resultSet, i) -> {
        return new Category(resultSet.getInt("CATEGORY_ID"),
                resultSet.getInt("USER_ID"),
                resultSet.getString("TITLE"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDouble("TOTAL_EXPENSE"));
    });
}
