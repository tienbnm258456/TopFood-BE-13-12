package com.demo.shop.response;

import java.util.List;

import com.demo.shop.entity.Role;

public class RoleResponse {

	private List<Role> roles;

	public RoleResponse(List<Role> roles) {
		super();
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
