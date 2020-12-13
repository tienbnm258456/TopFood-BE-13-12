package com.demo.shop.service.impl;

import javax.transaction.Transactional;

import com.demo.shop.constant.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.User;
import com.demo.shop.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserNameAndStatus(userName, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

		return UserDetailsImpl.build(user);
	}

}
