package com.demo.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.shop.entity.Role;
import com.demo.shop.request.RoleRequest;

@Service
public interface RoleService {

	public List<Role> getAll();

    public Role getById(Integer id);

    public Role add(RoleRequest roleRequest);

    public Role update(Integer id, RoleRequest roleRequest);

    public void delete(Integer id);
    
    public Optional<Role> getByRolNombre(String name);
      
    
}
