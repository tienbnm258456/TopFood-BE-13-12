package com.demo.shop.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.Report;

@Repository
public interface ReportReponsitory extends JpaRepository<Report, Integer> {

	List<Report> findByStatus(Integer status);

	Optional<Report> findByIdAndStatus(Integer id, Integer status);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Report r SET r.reply = :reply WHERE r.id = :id")
	public void updateReport(@Param("reply") String reply, @Param("id") int id);
	
}
