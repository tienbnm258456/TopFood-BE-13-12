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

import com.demo.shop.entity.Product;
import com.demo.shop.entity.Supplier;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.repository.SupplierRepository;
import com.demo.shop.request.SupplierRequest;
import com.demo.shop.response.SupplierResponse;
import com.demo.shop.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<SupplierResponse> getAll() {
		List<Supplier> suppliers = supplierRepository.findByStatus(StatusConstant.STATUS_ACTIVE);
		List<SupplierResponse> supplierResponses = new ArrayList<>();
		for (Supplier supplier : suppliers) {
			SupplierResponse response = new SupplierResponse();
			response.setSupplierName(supplier.getSupplierName());
			response.setId(supplier.getId());
			response.setStatus(supplier.getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if (supplier.getCreateDate() != null) {
				response.setCreateDate(supplier.getCreateDate().format(formatter));
			}
			if (supplier.getUpdateAt() != null) {
				response.setUpdateAt(supplier.getUpdateAt().format(formatter));
			}
			supplierResponses.add(response);
		}
		return supplierResponses;
	}

	@Override
	public SupplierResponse getById(Integer id) {
		SupplierResponse response = new SupplierResponse();
		Optional<Supplier> supplier = supplierRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE);
		if (supplier != null) {
			response.setSupplierName(supplier.get().getSupplierName());
			response.setId(supplier.get().getId());
			response.setStatus(supplier.get().getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if (supplier.get().getCreateDate() != null) {
				response.setCreateDate(supplier.get().getCreateDate().format(formatter));
			}
			if (supplier.get().getUpdateAt() != null) {
				response.setUpdateAt(supplier.get().getUpdateAt().format(formatter));
			}
		}
		return response;
	}

	@Override
	public Supplier add(SupplierRequest supplierRequest) {
		Supplier supplier = new Supplier();
		supplier.setSupplierName(supplierRequest.getSupplierName());
		supplier.setStatus(StatusConstant.STATUS_ACTIVE);
		supplier.setCreateDate(LocalDateTime.now());
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier update(SupplierRequest supplierRequest, Integer id) {
		Supplier supplier = supplierRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("Supplier is not found = " + id));
		supplier.setSupplierName(supplierRequest.getSupplierName());
		supplier.setUpdateAt(LocalDateTime.now());
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier delete(Integer id)throws ExceptionHandler   {
		Supplier supplier = supplierRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("Supplier is not found = " + id));
		List<Product> productList = productRepository.findProductsBySupplierId(id);
		if(!productList.isEmpty()){
			throw new ExceptionHandler("Tồn tại Product trong Supplier");
		}else{
			supplier.setStatus(StatusConstant.STATUS_INACTIVE);;
			return supplierRepository.save(supplier);
		}
	}

	@Override
	public List<SupplierResponse> searchBySupplierName(String supplierName) {
		List<Supplier> suppliers = supplierRepository.findAllBySupplierName(supplierName);
		List<SupplierResponse> supplierResponelist = new ArrayList<>();
		for (Supplier sup : suppliers) {
			SupplierResponse supplierDTO = new SupplierResponse();
			if (suppliers != null) {
				supplierDTO.setId(sup.getId());
				supplierDTO.setSupplierName(sup.getSupplierName());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				if (sup.getCreateDate() != null) {
					supplierDTO.setCreateDate(sup.getCreateDate().format(formatter));

				}
				if (sup.getUpdateAt() != null) {
					supplierDTO.setUpdateAt(sup.getUpdateAt().format(formatter));

				}
				supplierDTO.setStatus(sup.getStatus());

			}
			supplierResponelist.add(supplierDTO);
		}
		return supplierResponelist;
	}

}
