package com.demo.shop.entity;

import java.util.List;

public class TokenDto {
	String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private List<String> roles;
	
	

    public TokenDto(String token, Integer id, String username, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}


	public String getType() {
		return type;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }
}
