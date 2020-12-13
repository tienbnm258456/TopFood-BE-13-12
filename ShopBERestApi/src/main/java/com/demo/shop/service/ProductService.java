package com.demo.shop.service;

import java.io.IOException;
import java.util.List;

import com.demo.shop.entity.Product;
import com.demo.shop.request.ProductRequest;
import com.demo.shop.response.ProductResponse;
import com.demo.shop.response.TotalProductResponse;

public interface ProductService {
    public List<ProductResponse> getAll();
    public Product add(ProductRequest productRequest) throws IOException;
    public ProductResponse getById(Integer id);
    public Product update(Integer id, ProductRequest productRequest) throws IOException;
    public Product delete(Integer id);
    public List<ProductResponse> getProductByCateGoryId(Integer categoryId);
    public List<ProductResponse> getProductByCateGoryIdTop4(Integer categoryId);
    public List<ProductResponse> searchByProductName(String productName);
    public List<ProductResponse> getTop4Product();
    public List<ProductResponse> getTop8Fruit();
    public TotalProductResponse countProduct();

    ProductResponse create(ProductRequest request) throws IOException;
}

