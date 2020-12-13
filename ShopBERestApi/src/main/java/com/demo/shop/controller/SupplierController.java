package com.demo.shop.controller;

import java.util.List;

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
import org.springframework.web.server.ResponseStatusException;

import com.demo.shop.entity.Supplier;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.request.SupplierRequest;
import com.demo.shop.response.SupplierResponse;
import com.demo.shop.service.SupplierService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@GetMapping("/supplier")
	public ResponseEntity<?> getAll() {
		List<SupplierResponse> suppliers = supplierService.getAll();
		return new ResponseEntity<>(suppliers, HttpStatus.OK);
	}
	@GetMapping("/supplier/search")
	public ResponseEntity<List<SupplierResponse>> search(@RequestParam(value = "supplierName", required = false, defaultValue = "") String supplierName) {
		return ResponseEntity.ok().body(supplierService.searchBySupplierName(supplierName));
	}

	@GetMapping("/supplier/{id}")
	public ResponseEntity<SupplierResponse> getById(@PathVariable(value = "id") Integer id) {
		return ResponseEntity.ok().body(supplierService.getById(id));
	}

	@PostMapping("/supplier")
	public ResponseEntity<Supplier> create(@RequestBody SupplierRequest supplierRequest) {
		return ResponseEntity.ok().body(supplierService.add(supplierRequest));
	}

	@PutMapping("/supplier/{id}")
	public ResponseEntity<Supplier> update(@RequestBody SupplierRequest supplierRequest,
										   @PathVariable(value = "id") Integer id) {
		return ResponseEntity.ok().body(supplierService.update(supplierRequest, id));
	}

	@DeleteMapping("/supplier/{id}")
	public ResponseEntity<?> detele(@PathVariable(value = "id") Integer id) throws ExceptionHandler {
		try {
			supplierService.delete(id);
			return ResponseEntity.ok(HttpStatus.OK);
		}
		catch (ExceptionHandler e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}
}
