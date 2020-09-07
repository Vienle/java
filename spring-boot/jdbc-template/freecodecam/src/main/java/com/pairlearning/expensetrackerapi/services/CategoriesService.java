package com.pairlearning.expensetrackerapi.services;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.exceptions.EtResouceNotFoundException;

import java.util.List;

public interface CategoriesService {
    List<Category> fetchAllCategories(int userId) throws EtResouceNotFoundException;

    Category fetchCategoryByUserIdAndId(int userId, int categoryId) throws EtResouceNotFoundException;

    Category addCategory(int userId,String title,String description) throws EtResouceNotFoundException;

    void updateCategory(int userId,int categoryId, Category category) throws EtResouceNotFoundException;

    void removeCategoryAllTransactions(int userId,int categoryId);
}
