package com.demo.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	
	List<Supplier> findByStatus(Integer status);
	
	Optional<Supplier> findByIdAndStatus(Integer id, Integer status);

	@Query("SELECT s FROM Supplier s where s.status = :status and s.id = :id")
	List<Supplier> getAllById(Integer status, Integer id);
	
	@Query(value = "SELECT s FROM Supplier s WHERE s.supplierName LIKE CONCAT('%',:supplierName,'%') AND s.status = 1")
    List<Supplier> findAllBySupplierName(String supplierName);
}
