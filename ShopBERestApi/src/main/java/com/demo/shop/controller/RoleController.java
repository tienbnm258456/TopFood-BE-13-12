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
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.Role;
import com.demo.shop.request.RoleRequest;
import com.demo.shop.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/role")
	public ResponseEntity<?> getAll() {
		List<Role> roles = roleService.getAll();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
	@GetMapping("/role/{id}")
	public ResponseEntity<Role> getById(@PathVariable(value = "id") Integer id) {
		return ResponseEntity.ok().body(roleService.getById(id));
	}
	
	@PostMapping("/role")
	public ResponseEntity<Role> create(@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok().body(roleService.add(roleRequest));
	}

	@PutMapping("/role/{id}")
	public ResponseEntity<Role> update(@RequestBody RoleRequest roleRequest,
			@PathVariable(value = "id") Integer id) {
		return ResponseEntity.ok().body(roleService.update(id, roleRequest));
	}
	
	@DeleteMapping("/role/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		roleService.delete(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
