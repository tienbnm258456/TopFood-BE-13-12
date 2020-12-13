package com.demo.shop.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.shop.constant.RoleConstant;
import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Role;
import com.demo.shop.entity.User;
import com.demo.shop.repository.RoleRepository;
import com.demo.shop.repository.UserRepository;
import com.demo.shop.request.LoginRequest;
import com.demo.shop.request.UpdateUserRequest;
import com.demo.shop.request.UserRequest;
import com.demo.shop.response.TotalUserResponse;
import com.demo.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public List<User> getAll() {
		return userRepository.findByStatus(StatusConstant.STATUS_ACTIVE);
	}

	@Override
	public User delete(Integer id, LoginRequest loginRequest) {
		User user = userRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("User with not found = " + id));
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if(userDetails.getUsername().equalsIgnoreCase(user.getUserName())) {
//			log.debug("Do not delete id" + id);
		}
		else {
			user.setStatus(0);
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public Optional<User> findOne(String userName) {
		return userRepository.findByUserNameAndStatus(userName, StatusConstant.STATUS_ACTIVE);
	}

	@Override
	public User getById(Integer id) {
		User user = userRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("User with not found = " + id));
		return user;
	}

	@Override
	public User update(Integer id, UserRequest userRequest) {
		User user = userRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("user with not found id  = " + id));
		user.setUserName(userRequest.getUserName());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhone(userRequest.getPhone());
		user.setGender(userRequest.getGender());
		user.setEmail(userRequest.getEmail());
		user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
		user.setUpdateAt(new Date());

		Set<String> strRoles = userRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {

					case "admin":
						Role adminRole = roleRepository.findByName("ROLE_ADMIN")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					default:
						Role userRole = roleRepository.findByName("ROLE_USER")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		return userRepository.save(user);
	}

	@Override
	public TotalUserResponse totalUser() {
		Integer totalUser = userRepository.totalUser(StatusConstant.STATUS_ACTIVE, RoleConstant.ROLE_USER);
		TotalUserResponse response = new TotalUserResponse();
		response.setTotalUser(totalUser);
		return response;
	}


//	@Override
//	public List<UserResponse> searchbyUserName(String userName) {
//		List<User> users = userRepository.findAllByUserName(userName);
//		List<UserResponse> userReponseList = new ArrayList<>();
//		
//		for(User u: users) {
//			UserResponse userDTO = new UserResponse();
//		 if(users != null) {
//		userDTO.setId(u.getId());
//		userDTO.setFirstName(u.getFirstName());
//		userDTO.setPassword(u.getPassword());
//		userDTO.setPhone(u.getPhone());
//		userDTO.setLastName(u.getLastName());
//		userDTO.setEmail(u.getPhone());
//		userDTO.setStatus(u.getStatus());
//		userDTO.setUpdateAt(u.getUpdateAt());
//		userDTO.setCreateDate(u.getCreateDate());;
//
//		
//		 }
//		 userReponseList.add(userDTO);
//		}
//	return userReponseList;
//	}
	
	@Override
	public User updateUser(Integer id, UpdateUserRequest userRequest) {
		User user = userRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("user with not found id  = " + id));
		user.setUserName(userRequest.getUserName());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhone(userRequest.getPhone());
		user.setGender(userRequest.getGender());
		user.setEmail(userRequest.getEmail());
		user.setUpdateAt(new Date());
		return userRepository.save(user);
	}

}
