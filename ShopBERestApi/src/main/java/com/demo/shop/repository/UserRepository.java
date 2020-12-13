package com.demo.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	
	Optional<User> findByUserNameAndStatus(String userName, Integer status);
	
	Boolean existsByUserName(String userName);

	Boolean existsByEmail(String email);
	
	List<User> findByStatus(Integer status);
	
	Optional<User> findByIdAndStatus(Integer id, Integer status);
	
	Optional<User> findByEmail(String email);
	
//	@Query(value = "SELECT us FROM  User us WHERE us.userName LIKE CONCAT('%',:userName,'%') AND us.status = 1")
//    List<User> findAllByUserName(String userName);
	
	@Query(value = "select count(*) \r\n"
			+ "from `user` u inner join user_roles ur ON u.id = ur.user_id \r\n"
			+ "			  inner join `role` r2 on ur.role_id = r2.id \r\n"
			+ "where u.status = :status and r2.name = :name ", nativeQuery = true)
	Integer totalUser(Integer status, String name);
}
