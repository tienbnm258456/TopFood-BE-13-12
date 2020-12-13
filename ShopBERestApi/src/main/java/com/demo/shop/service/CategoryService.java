package com.demo.shop.service;

import java.util.List;

import com.demo.shop.entity.Category;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.request.CategoryRequest;
import com.demo.shop.response.CategoryResponse;
import com.demo.shop.response.TotalCategoryResponse;

public interface CategoryService {
	public List<CategoryResponse> getAll();

	public CategoryResponse getById(Integer id);

	public Category add(CategoryRequest categoryRequest);

	public Category update(Integer id, CategoryRequest categoryRequest);

	public Category delete(Integer id) throws ExceptionHandler;

	public List<CategoryResponse> searchByCategoryName(String categoryName);

	public TotalCategoryResponse totalCategory();

	public List<CategoryResponse> top4Category();
}
