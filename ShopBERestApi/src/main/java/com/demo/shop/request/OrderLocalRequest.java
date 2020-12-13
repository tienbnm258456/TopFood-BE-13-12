package com.demo.shop.request;

import java.util.List;

public class OrderLocalRequest {

    private Integer userId;

    private Integer price;

    private String description;

    private List<OrderDetailRequest> orderDetailRequests;

    private String name;

    private String address;

    private String phone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }

    public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
