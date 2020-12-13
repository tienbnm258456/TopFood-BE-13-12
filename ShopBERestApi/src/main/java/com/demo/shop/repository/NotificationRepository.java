package com.demo.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.shop.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	@Query(value = "select p.id, n.descriptions, n.create_date, n.product_id, n.storage_time, n.update_date  \r\n"
			+ "from product p inner join notification n on p.id = n.product_id\r\n"
			+ "group by p.id\r\n"
			+ "order by n.create_date desc", nativeQuery = true)
	List<Notification> findNotificationByDate();
}
