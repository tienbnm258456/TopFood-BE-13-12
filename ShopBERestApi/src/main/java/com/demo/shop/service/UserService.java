package com.demo.shop.service;

import java.util.List;
import java.util.Optional;

import com.demo.shop.entity.User;
import com.demo.shop.request.LoginRequest;
import com.demo.shop.request.UpdateUserRequest;
import com.demo.shop.request.UserRequest;
import com.demo.shop.response.TotalUserResponse;

public interface UserService {
	
	public User update(Integer id,UserRequest user);
	
	public List<User> getAll();
	
	public User delete(Integer id,  LoginRequest loginRequest);
    
    public Optional<User> findOne(String username);

    public User getById(Integer id);
    
//    public List<UserResponse> searchbyUserName(String userName);
    
    public TotalUserResponse totalUser();
    
    public User updateUser(Integer id, UpdateUserRequest user);
}
