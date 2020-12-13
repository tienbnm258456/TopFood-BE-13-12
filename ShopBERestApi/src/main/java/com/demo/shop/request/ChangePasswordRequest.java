package com.demo.shop.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

	private String userName;
	private String password;
	private String passwordNew; 
	private String confirmNewPassword;
	
}
