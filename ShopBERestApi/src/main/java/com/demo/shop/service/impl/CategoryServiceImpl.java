package com.demo.shop.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.demo.shop.constant.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.Category;
import com.demo.shop.entity.Product;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.repository.CategoryRepository;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.request.CategoryRequest;
import com.demo.shop.response.CategoryResponse;
import com.demo.shop.response.TotalCategoryResponse;
import com.demo.shop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<CategoryResponse> getAll() {
		List<Category> categories = categoryRepository.findByStatus(StatusConstant.STATUS_ACTIVE);
		List<CategoryResponse> categoryResponses = new ArrayList<>();
		for (Category category : categories) {
			CategoryResponse response = new CategoryResponse();
			response.setId(category.getId());
			response.setCategoryName(category.getCategoryName());
			response.setStatus(category.getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if (category.getCreateDate() != null) {
				response.setCreateDate(category.getCreateDate().format(formatter));
			}
			if (category.getUpdateAt() != null) {
				response.setUpdateAt(category.getUpdateAt().format(formatter));
			}
			categoryResponses.add(response);
		}
		return categoryResponses;
	}

	@Override
	public CategoryResponse getById(Integer id) {
		CategoryResponse response = new CategoryResponse();
		Optional<Category> category = categoryRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE);
		if (category != null) {
			response.setId(category.get().getId());
			response.setCategoryName(category.get().getCategoryName());
			response.setStatus(category.get().getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if (category.get().getCreateDate() != null) {
				response.setCreateDate(category.get().getCreateDate().format(formatter));
			}
			if (category.get().getUpdateAt() != null) {
				response.setUpdateAt(category.get().getUpdateAt().format(formatter));
			}
		}
		return response;
	}

	@Override
	public Category add(CategoryRequest categoryRequest) {
		Category category = new Category();
		category.setCategoryName(categoryRequest.getCategoryName());
		category.setStatus(StatusConstant.STATUS_ACTIVE);
		category.setCreateDate(LocalDateTime.now());
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Integer id, CategoryRequest categoryRequest) {
		Category category = categoryRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("Category with not found id = " + id));
		category.setCategoryName(categoryRequest.getCategoryName());
		category.setUpdateAt(LocalDateTime.now());
		return categoryRepository.save(category);
	}

	@Override
	public Category delete(Integer id) throws ExceptionHandler {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with not found id = " + id));
		List<Product> productList = productRepository.findProductsByCategoryId(id);
		if(!productList.isEmpty()){
			throw new ExceptionHandler("Tồn tại Product trong Category");
		}else{
			category.setStatus(StatusConstant.STATUS_INACTIVE);;
			return categoryRepository.save(category);
		}
	}

	@Override
	public List<CategoryResponse> searchByCategoryName(String categoryName) {
		List<Category> categorys = categoryRepository.findAllByCategoryName(categoryName);
		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		for (Category ca : categorys) {
			CategoryResponse categoryDTO = new CategoryResponse();
			if (categorys != null) {
				categoryDTO.setId(ca.getId());
				categoryDTO.setCategoryName(ca.getCategoryName());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				if (ca.getCreateDate() != null) {
					categoryDTO.setCreateDate(ca.getCreateDate().format(formatter));
				}
				if (ca.getUpdateAt() != null) {
					categoryDTO.setUpdateAt(ca.getUpdateAt().format(formatter));
				}
				categoryDTO.setStatus(ca.getStatus());
			}
			categoryResponseList.add(categoryDTO);
		}
		return categoryResponseList;
	}

	@Override
	public TotalCategoryResponse totalCategory() {

		Integer totalCategory = categoryRepository.totalCategory(StatusConstant.STATUS_ACTIVE);
		TotalCategoryResponse categoryResponse = new TotalCategoryResponse();
		categoryResponse.setTotalCategory(totalCategory);
		return categoryResponse;
	}

	@Override
	public List<CategoryResponse> top4Category() {
		List<Category> categories = categoryRepository.findTop4Category(StatusConstant.STATUS_ACTIVE);
		List<CategoryResponse> categoryResponses = new ArrayList<>();
		for (Category category : categories) {
			CategoryResponse response = new CategoryResponse();
			response.setId(category.getId());
			response.setCategoryName(category.getCategoryName());
			response.setStatus(category.getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if (category.getCreateDate() != null) {
				response.setCreateDate(category.getCreateDate().format(formatter));
			}
			if (category.getUpdateAt() != null) {
				response.setUpdateAt(category.getUpdateAt().format(formatter));
			}
			categoryResponses.add(response);
		}
		return categoryResponses;
	}
}
