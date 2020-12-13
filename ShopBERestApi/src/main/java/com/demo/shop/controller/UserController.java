package com.demo.shop.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.User;
import com.demo.shop.repository.UserRepository;
import com.demo.shop.request.ChangePasswordRequest;
import com.demo.shop.request.LoginRequest;
import com.demo.shop.request.UpdateUserRequest;
import com.demo.shop.request.UserRequest;
import com.demo.shop.response.MessageResponse;
import com.demo.shop.service.UserService;
import com.demo.shop.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public ResponseEntity<?> getAll() {
		List<User> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer id) {
		User user = userService.getById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id, @RequestBody UserRequest userRequest) {
		User user = userService.update(id, userRequest);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id, @RequestBody  LoginRequest loginRequest) {
		User user = userService.delete(id,loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
		
		if(userDetails.getUsername().equalsIgnoreCase(user.getUserName())) {
			return new ResponseEntity<>(new MessageResponse("Do not delete id: " + id), HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(new MessageResponse("Success"),HttpStatus.OK);
		}
	}

	@GetMapping("/totalUser")
	public ResponseEntity<?> totalUser() {
		return ResponseEntity.ok().body(userService.totalUser());
	}

	@PutMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		User user = userRepository
				.findByUserNameAndStatus(changePasswordRequest.getUserName(), StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Không tìm thấy username: " + changePasswordRequest.getUserName()));

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
			if (changePasswordRequest.getPassword().equals(changePasswordRequest.getPasswordNew())
					|| changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmNewPassword())
					|| ((changePasswordRequest.getPassword().equals(changePasswordRequest.getPasswordNew())
					&& changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmNewPassword())))) {
				return new ResponseEntity<>(new MessageResponse("Mật khẩu cũ không được khớp với mật khẩu mới"),
						HttpStatus.BAD_REQUEST);
			} else if (!changePasswordRequest.getPasswordNew().equals(changePasswordRequest.getConfirmNewPassword())) {
				return new ResponseEntity<>(new MessageResponse("Xác nhận mật khẩu không khớp với mật khẩu mới"),
						HttpStatus.BAD_REQUEST);
			} else {
				user.setPassword(bcryptEncoder.encode(changePasswordRequest.getPasswordNew()));
//				user.setFirstName(user.getFirstName());
//				user.setLastName(user.getLastName());
//				user.setPhone(user.getPhone());
//				user.setGender(user.getGender());
//				user.setEmail(user.getEmail());
//				user.setStatus(StatusConstant.STATUS_ACTIVE);
//				user.setCreateDate(new Date());
				user.setUpdateAt(new Date());
				userRepository.save(user);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("Mật khẩu không chính xác"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new MessageResponse("Đổi mật khẩu thành công"), HttpStatus.OK);
	}
	
	@PutMapping("/update-user")
	public ResponseEntity<?> updateUser(@RequestParam("userId") Integer id, @RequestBody UpdateUserRequest userRequest) {
		User user = userService.updateUser(id, userRequest);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("info-user")
	public ResponseEntity<?> getByUserId(@RequestParam("userId") Integer id) {
		User user = userService.getById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
