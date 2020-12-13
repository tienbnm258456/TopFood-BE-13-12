/**
 * 
 */
package com.demo.shop.response;

import java.util.List;

import com.demo.shop.entity.User;

/**
 * @author: tuha.lvt
 *
 * @date: 10/2/2020
 */
public class UserResponse {

	private List<User> users;

	/**
	 * @param users
	 */
	public UserResponse(List<User> users) {
		super();
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
