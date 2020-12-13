package com.demo.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.Role;
import com.demo.shop.repository.RoleRepository;
import com.demo.shop.request.RoleRequest;
import com.demo.shop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role getById(Integer id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Role with not found id  = " + id));
		return role;
	}

	@Override
	public Role add(RoleRequest roleRequest) {

		Role role = new Role();
		role.setName(roleRequest.getName());
		role.setCreateDate(new Date());
		return roleRepository.save(role);
	}

	@Override
	public Role update(Integer id, RoleRequest roleRequest) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Role with not found id  = " + id));
		role.setName(roleRequest.getName());
		role.setUpdateAt(new Date());

		return roleRepository.save(role);
	}

	@Override
	public void delete(Integer id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Role with not found id  = " + id));
		roleRepository.delete(role);

	}

	@Override
	public Optional<Role> getByRolNombre(String name) {
		return roleRepository.findByName(name);
	}

}
