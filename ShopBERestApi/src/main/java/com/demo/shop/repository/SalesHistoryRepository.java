package com.demo.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.Order;
import com.demo.shop.response.SalesHistoryAllData;
import com.demo.shop.response.SalesHistoryData;
import com.demo.shop.response.SalesHistoryDetailData;

@Repository
public interface SalesHistoryRepository extends JpaRepository<Order, Integer> {

	@Query(value = "SELECT o.id as orderId, u.user_name as userName, o.total_price as totalPrice, " + //
			"sum(od.quantity) as countQuantity, o.created_date as createdDate, " + //
			"o.name, o.`description`, o.address, o.phone " + //
			"FROM topfood.`order` o  " + //
			"	INNER JOIN order_detail od ON od.order_id = o.id " + //
			"    INNER JOIN product p ON od.product_id = p.id " + //
			"    INNER JOIN `user` u ON u.id = o.user_id " + //
			"    WHERE u.id = :userId AND o.id like concat('%', :order, '%')" + //
			"    GROUP BY o.id", nativeQuery = true) //
	List<SalesHistoryData> findHistoryByUser(Integer userId, String order);
	
	@Query(value = "SELECT o.id as orderId, u.user_name as userName, o.created_date as createdDate, " + 
			"	   o.name, o.`description`, o.address, o.phone ,  sum(od.quantity) as countQuantity, " + 
			"       o.total_price as totalPrice, o.status " + //
			"			FROM `order` o  " + //
			"				INNER JOIN order_detail od ON od.order_id = o.id " + //
			"			    INNER JOIN product p ON od.product_id = p.id " + //
			"			    INNER JOIN `user` u ON u.id = o.user_id " + //
			"			WHERE o.id LIKE CONCAT('%', :order , '%')" + //
			"			GROUP BY o.id " + //
			"			ORDER BY u.id asc", nativeQuery = true) //
	List<SalesHistoryAllData> findHistoryAll(String order);

	@Query(value = "SELECT od.id as orderDetailId, o.id as orderId, u.user_name as userName, p.product_name as productName,    " + //
			"	 (p.price *SUM(od.quantity)) as totalPrice,  " + //
			"	 SUM(od.quantity) as countQuantity, " + //
			"     o.created_date as createdDate, o.name, o.`description`, o.address, o.phone " + //
			"FROM `order` o  " + //
			"	 INNER JOIN order_detail od ON od.order_id = o.id " + //
			"    INNER JOIN product p ON od.product_id = p.id " + //
			"    INNER JOIN `user` u ON u.id = o.user_id " + //
			"    WHERE od.order_id = :orderId AND od.id like concat('%', :orderDetail, '%')" + //
			"    GROUP BY p.product_name", nativeQuery = true) //
	List<SalesHistoryDetailData> findHistoryDetailByUser(Integer orderId, String orderDetail);

}
