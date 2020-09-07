package com.pairlearning.expensetrackerapi.enpoint;

import com.pairlearning.expensetrackerapi.entities.Category;
import com.pairlearning.expensetrackerapi.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoriesEnpoint {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<Object> getAllCategories(HttpServletRequest request){
        int userID = (Integer) request.getAttribute("userId");
        List<Category> categories = categoriesService.fetchAllCategories(userID);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<Object> addCategory(HttpServletRequest request,
                                              @RequestBody Map<String,Object> categoryMap){
        int userId = (Integer) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category category = categoriesService.addCategory(userId,title,description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,@PathVariable Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        Category category = categoriesService.fetchCategoryByUserIdAndId(userId,categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping("{/categoryId}")
    public ResponseEntity<Object> updateCategory(HttpServletRequest request,
                                                 @RequestBody Category category,
                                                 @PathVariable("categoryId") Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        categoriesService.updateCategory(userId,categoryId,category);
        return new ResponseEntity<>("true",HttpStatus.OK);
    }
}
