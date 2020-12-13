package com.demo.shop.repository;

import com.demo.shop.entity.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findAllByUserIdAndBought(Integer userId, Integer bought);

	boolean existsByUserIdAndProductIdAndBought(Integer userId, Integer productId, Integer bought);

	@Modifying(clearAutomatically = true)
	@Query("update Cart c set c.bought = 1 where c.userId =:userId")
	void updateBoughtByUserId(@Param("userId") Integer userId);

	int countAllByUserIdAndBought(Integer userId, Integer bought);
}
