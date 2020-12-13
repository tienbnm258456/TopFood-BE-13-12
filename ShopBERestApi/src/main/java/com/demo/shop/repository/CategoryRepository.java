package com.demo.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	List<Category> findByStatus(Integer status);
	
	Optional<Category> findByIdAndStatus(Integer id, Integer status);

	@Query("SELECT c FROM Category c where c.status = :status and c.id = :id")
	List<Category> getAllByCategoryId(Integer status, Integer id);
	
	@Query(value = "SELECT c FROM Category c WHERE c.categoryName LIKE CONCAT('%',:categoryName,'%') AND c.status = 1")
    List<Category> findAllByCategoryName(String categoryName);
	
	@Query(value ="select count(*) from Category c where status = :status", nativeQuery = true)
    Integer totalCategory(Integer status);
	
	@Query(value = "select * \r\n"
			+ " from category c  inner join product p on c.id = p.category_id\r\n"
			+ " where c.status = :status\r\n"
			+ " group by c.category_name\r\n"
			+ " order by p.quantity desc\r\n"
			+ " limit 3", nativeQuery = true)
    List<Category> findTop3Category( Integer status);

	@Query(value = "select * \r\n"
			+ " from category c  inner join product p on c.id = p.category_id\r\n"
			+ " where c.status = :status\r\n"
			+ " group by c.category_name\r\n"
			+ " order by p.quantity desc\r\n"
			+ " limit 4", nativeQuery = true)
    List<Category> findTop4Category( Integer status);
}
