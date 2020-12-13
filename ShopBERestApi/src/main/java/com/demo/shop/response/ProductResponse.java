package com.demo.shop.response;

import java.util.Date;

import com.demo.shop.entity.Category;

import com.demo.shop.entity.Supplier;
import lombok.Data;

@Data
public class ProductResponse {
    private Integer id;

    private String productName;

    private float price;

    private String description;

    private Integer status;

    private double priceSale;
    
    private byte[] priByte;

    private String createdDate;

    private String updatedDate;

    private Category category;

    private Supplier supplier;

	private String image;



}
