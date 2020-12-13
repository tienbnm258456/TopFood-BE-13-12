package com.demo.shop.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.Product;
import com.demo.shop.request.ProductRequest;
import com.demo.shop.response.ProductResponse;
import com.demo.shop.service.OrderService;
import com.demo.shop.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @GetMapping("/products")
    public ResponseEntity<?> getAll() {
    	List<ProductResponse> list = productService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getAllById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(productService.getById(id));
    }

    @PostMapping("/product")
    public ResponseEntity<Product> add(@RequestBody ProductRequest productRequest) throws IOException {
        return ResponseEntity.ok().body(productService.add(productRequest));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(@PathVariable(value = "id") Integer id,
                                          @RequestBody ProductRequest productRequest) throws IOException {
        return ResponseEntity.ok().body(productService.update(id,productRequest));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> delete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(productService.delete(id));
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductByCategoryId(@PathVariable(value = "categoryId") Integer categoryId){
        return ResponseEntity.ok().body(productService.getProductByCateGoryId(categoryId));
    }

    @GetMapping("/products/top4/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductByCategoryIdTop4(@PathVariable(value = "categoryId") Integer categoryId){
        return ResponseEntity.ok().body(productService.getProductByCateGoryIdTop4(categoryId));
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductResponse>> search(@RequestParam(value = "productName", required = false, defaultValue = "") String productName) {
        return ResponseEntity.ok().body(productService.searchByProductName(productName));
    }
    
    @GetMapping("/top4product")
	public ResponseEntity<List<ProductResponse>> getTop4Product() {
		return ResponseEntity.ok().body(productService.getTop4Product());
	}
	
	@GetMapping("/top8fruit")
	public ResponseEntity<List<ProductResponse>> getTop8Fruit() {
		return ResponseEntity.ok().body(productService.getTop8Fruit());
	}

    @GetMapping("/totalProduct")
    public ResponseEntity<?> totalProduct() {
    	return ResponseEntity.ok().body(productService.countProduct());
    }

    @PostMapping("product/create")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        try {
            return ResponseEntity.ok().body(productService.create(request));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("revenuaByDay")
    public ResponseEntity<?> revenuaByDay() {
        return ResponseEntity.ok().body(orderService.revenuaByDay());
    }
    @GetMapping("revenuaByWeek")
    public ResponseEntity<?> revenuaByWeek() {
        return ResponseEntity.ok().body(orderService.revenuaByWeek());
    }

    @GetMapping("revenuaByMonth")
    public ResponseEntity<?> revenuaByMonth() {
        return ResponseEntity.ok().body(orderService.revenuaByMonth());
    }

    @GetMapping("revenuaByYear")
    public ResponseEntity<?> revenuaByYear() {
        return ResponseEntity.ok().body(orderService.revenuaByYear());
    }

    @GetMapping("ordersByDay")
    public ResponseEntity<?> getOrdersByDay() {
        return ResponseEntity.ok().body(orderService.getListOrderByDay());
    }

    @GetMapping("ordersByWeek")
    public ResponseEntity<?> getOrdersByWeek() {
        return ResponseEntity.ok().body(orderService.getListOrderByWeek());
    }

    @GetMapping("ordersByMonth")
    public ResponseEntity<?> getOrdersByMonth() {
        return ResponseEntity.ok().body(orderService.getListOrderByMonth());
    }

    @GetMapping("ordersByYear")
    public ResponseEntity<?> getOrdersByYear() {
        return ResponseEntity.ok().body(orderService.getListOrderByYear());
    }

}
