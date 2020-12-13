package com.demo.shop.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

	private String userName;

	private String firstName;

	private String lastName;

	private String phone;

	private int gender;

	private String email;

}
