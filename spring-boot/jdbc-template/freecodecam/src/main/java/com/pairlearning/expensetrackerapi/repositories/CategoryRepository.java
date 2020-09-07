package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.exceptions.EtBadRequestException;
import com.pairlearning.expensetrackerapi.exceptions.EtResouceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws EtResouceNotFoundException;

    Category findById(Integer userId, Integer categoryId) throws EtResouceNotFoundException;

    Integer create(Integer userId, String title, String description) throws EtBadRequestException;

    void update(int userId, int categoryId, Category category) throws EtResouceNotFoundException;

    void remove(int userId, int categoryId);
}
