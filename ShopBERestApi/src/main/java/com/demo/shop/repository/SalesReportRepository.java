package com.demo.shop.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.demo.shop.entity.Cart;
import com.demo.shop.response.SalesReportData;

@Repository
public interface SalesReportRepository extends JpaRepository<Cart, Integer> {

	@Query(value = " SELECT p.id as productId, p.product_name as productName, " + //
			"  (p.price *SUM(od.quantity)) as totalPrice,  " + //
			"  SUM(od.quantity) as countQuantity " + //
			" FROM topfood.order_detail od INNER JOIN topfood.order o ON od.order_id = o.id " + //
			" INNER JOIN product p ON od.product_id = p.id " + //
			" WHERE o.created_date BETWEEN :fromTime AND :toTime " + //
			" GROUP BY p.id, p.product_name " + //
			" ORDER BY p.id", nativeQuery = true) //
	List<SalesReportData> querySalesReport(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);

	@Query(value = " SELECT p.id as productId, p.product_name as productName, " + //
			"  (p.price *SUM(od.quantity)) as totalPrice,  " + //
			"  SUM(od.quantity) as countQuantity" + //
			" FROM order_detail od INNER JOIN `order` o ON od.order_id = o.id " + //
			" INNER JOIN product p ON od.product_id = p.id " + //
			" WHERE o.created_date BETWEEN :fromTime AND :toTime " + //
			" GROUP BY p.id, p.product_name " + //
			" ORDER BY p.id;", nativeQuery = true) //
	List<SalesReportData> querySalesReportByPeriod(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);
}
