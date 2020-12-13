package com.demo.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.OrderDetail;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer>{

}
