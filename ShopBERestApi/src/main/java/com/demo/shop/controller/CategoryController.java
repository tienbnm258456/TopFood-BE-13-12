package com.demo.shop.controller;

import java.util.List;

import com.demo.shop.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.Category;
import com.demo.shop.request.CategoryRequest;
import com.demo.shop.response.CategoryResponse;
import com.demo.shop.service.CategoryService;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category")
    public ResponseEntity<?> getAll() {
        List<CategoryResponse> categories = categoryService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(categoryService.getById(id));
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(categoryService.add(categoryRequest));
    }

    @GetMapping("/category/search")
    public ResponseEntity<List<CategoryResponse>> search(@RequestParam(value = "categoryName", required = false, defaultValue = "") String categoryName) {
        return ResponseEntity.ok().body(categoryService.searchByCategoryName(categoryName));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> update(@RequestBody CategoryRequest categoryRequest,
                                           @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) throws ExceptionHandler {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (ExceptionHandler e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
        }
    }

    @GetMapping("/totalCategory")
    public  ResponseEntity<?> totalCategory() {
        return ResponseEntity.ok().body(categoryService.totalCategory());
    }

    @GetMapping("/top4category")
    public ResponseEntity<?> top4category() {
        return ResponseEntity.ok().body(categoryService.top4Category());
    }

}
