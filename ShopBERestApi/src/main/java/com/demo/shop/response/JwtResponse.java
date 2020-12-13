package com.demo.shop.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private List<String> roles;
	
	public JwtResponse(String accessToken, Integer id, String username, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public JwtResponse(String token, String type, Integer id, String username, List<String> roles) {
		super();
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public JwtResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	
}
