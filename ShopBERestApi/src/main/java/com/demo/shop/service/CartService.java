package com.demo.shop.service;

import java.util.List;

import com.demo.shop.entity.Cart;
import com.demo.shop.exception.ExceptionHandler;
import com.demo.shop.request.CartRequest;
import com.demo.shop.response.CartResponse;

public interface CartService {

    List<CartResponse> findAllByUserId(Integer userId);

    void delete(Integer cartId);

    CartResponse create(CartRequest cartRequest);

    Integer countCartByUser(Integer userId);
}
