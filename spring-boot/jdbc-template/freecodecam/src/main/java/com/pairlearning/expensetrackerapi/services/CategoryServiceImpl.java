package com.pairlearning.expensetrackerapi.services;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.exceptions.EtResouceNotFoundException;
import com.pairlearning.expensetrackerapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoriesService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> fetchAllCategories(int userId) throws EtResouceNotFoundException {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryByUserIdAndId(int userId, int categoryId) throws EtResouceNotFoundException {
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public Category addCategory(int userId, String title, String description) throws EtResouceNotFoundException {
        Integer categoryId = categoryRepository.create(userId,title,description);
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public void updateCategory(int userId, int categoryId, Category category) throws EtResouceNotFoundException {
        categoryRepository.update(userId,categoryId,category);
    }

    @Override
    public void removeCategoryAllTransactions(int userId, int categoryId) {

    }
}
