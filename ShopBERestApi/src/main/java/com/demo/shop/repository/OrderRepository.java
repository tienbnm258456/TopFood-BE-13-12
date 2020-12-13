package com.demo.shop.repository;

import com.demo.shop.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByUserIdAndPaymentId(Integer userId, String paymentId);

    @Query(value = "select SUM(total_price) as tolalPrice " + "FROM `order` o2 "
            + "where DAY(created_date) = DAY(curdate());", nativeQuery = true)
    Integer revenueByDay();

    @Query(value = "select SUM(total_price) as tolalPrice " + "FROM `order` o2 "
            + "where WEEK(created_date) = WEEK(curdate());", nativeQuery = true)
    Integer revenueByWeek();

    @Query(value = "select SUM(total_price) as tolalPrice " + "FROM `order` o2 "
            + "where MONTH(created_date) = MONTH(curdate());", nativeQuery = true)
    Integer revenueByMonth();

    @Query(value = "select SUM(total_price) as tolalPrice " + "FROM `order` o2 "
            + "where YEAR(created_date) = YEAR(curdate());", nativeQuery = true)
    Integer revenueByYear();

    @Query(value = "select * "
            + "from `order` "
            + "where Day(created_date) = Day(curdate());", nativeQuery = true)
    List<Order> getListOrderByDay();

    @Query(value = "select * "
            + "from `order` "
            + "where Week(created_date) = Week(curdate());", nativeQuery = true)
    List<Order> getListOrderByWeek();

    @Query(value = "select * "
            + "from `order` "
            + "where month(created_date) = month(curdate());", nativeQuery = true)
    List<Order> getListOrderByMonth();

    @Query(value = "select * "
            + "from `order` "
            + "where year(created_date) = year(curdate());", nativeQuery = true)
    List<Order> getListOrderByYear();

}
