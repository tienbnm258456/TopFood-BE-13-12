package com.demo.shop.repository;

import com.demo.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    List<Product> findByStatus(Integer status);

    Optional<Product> findByIdAndStatus(Integer id, Integer status);
    
    @Query(value = "select * from Product where category_id = :categoryId AND status = 1", nativeQuery = true)
    List<Product> findProductsByCategoryId(Integer categoryId);

    @Query(value = "select * from Product where category_id = :categoryId AND status = 1 LIMIT 4", nativeQuery = true)
    List<Product> findProductsByCategoryIdTop4(Integer categoryId);
    
    @Query(value = "SELECT p.product_name, p.id, p.supplier_id, p.category_id, p.price, p.description, p.price_sale, p.status, p.image, p.quantity , p.create_date, p.update_date \r\n"
    		+ "FROM product p INNER JOIN supplier s ON p.supplier_id = s.id \r\n"
    		+ "WHERE p.supplier_id = :supplierId AND s.status = 1", nativeQuery = true)
    List<Product> findProductsBySupplierId(Integer supplierId);

    @Query(value = "SELECT p FROM Product p WHERE p.productName LIKE CONCAT('%',:productName,'%') AND p.status = 1")
    List<Product> findAllByProductName(String productName);
    
    @Query(value = "SELECT p.product_name, p.id, p.supplier_id, p.category_id, p.price, p.description, p.price_sale, p.status, p.image, p.quantity , p.create_date, p.update_date \r\n"
    		+ "FROM product p \r\n"
    		+ "where p.status = :status\r\n"
    		+ "order by p.quantity desc\r\n"
    		+ "limit 4", nativeQuery = true)
    List<Product> findTop4Product(Integer status);
    
    @Query(value = "SELECT p.product_name, p.id, p.supplier_id, p.category_id, p.price, p.description, p.price_sale, p.status, p.image, p.quantity , p.create_date, p.update_date \r\n"
    		+ "FROM product p \r\n"
    		+ "where p.status = :status and category_id = :category_id\r\n"
    		+ "order by p.quantity desc\r\n"
    		+ "limit 8", nativeQuery = true)
    List<Product> findTop8FruitProduct(Integer status, Integer category_id);
    
    @Query(value ="select count(*) from Product p where status = :status", nativeQuery = true)
    Integer CountProduct(Integer status);
}
