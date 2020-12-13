package com.demo.shop.service;

import java.util.List;

import com.demo.shop.entity.Supplier;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.request.SupplierRequest;
import com.demo.shop.response.SupplierResponse;

public interface SupplierService {

	public List<SupplierResponse> getAll();

	public SupplierResponse getById(Integer id);

	public Supplier add(SupplierRequest supplierRequest);

	public Supplier update(SupplierRequest supplierRequest, Integer id);

	public Supplier delete(Integer id) throws ExceptionHandler;

	public List<SupplierResponse> searchBySupplierName(String supplierName);
}
