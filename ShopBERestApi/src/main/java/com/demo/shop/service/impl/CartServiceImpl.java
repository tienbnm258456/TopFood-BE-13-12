package com.demo.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.demo.shop.constant.StatusConstant;
import com.demo.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.Cart;
import com.demo.shop.repository.CartRepository;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.request.CartRequest;
import com.demo.shop.response.CartResponse;
import com.demo.shop.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartResponse> findAllByUserId(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Cart> carts = cartRepository.findAllByUserIdAndBought(userId, StatusConstant.NOT_BOUGHT);
        return carts.stream().map(this::convertCartToCartResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer cartId) {
        cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cartRepository.deleteById(cartId);
    }

    @Override
    public CartResponse create(CartRequest cartRequest) {
        boolean test = cartRepository.existsByUserIdAndProductIdAndBought(cartRequest.getUserId(),
                cartRequest.getProductId(), StatusConstant.NOT_BOUGHT);
        if(test) {
            throw new EntityExistsException("Sản phẩm đã được thêm vào giỏ hàng");
        }
        productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Cart cart = new Cart();
        cart.setPrice(cartRequest.getPrice());
        cart.setProductId(cartRequest.getProductId());
        cart.setProductName(cartRequest.getProductName());
        cart.setQuantity(1);
        cart.setUserId(cartRequest.getUserId());
        cart.setBought(StatusConstant.NOT_BOUGHT);
        cart.setImage(cartRequest.getImage());
        return convertCartToCartResponse(cartRepository.save(cart));
    }

    @Override
    public Integer countCartByUser(Integer userId) {
        return cartRepository.countAllByUserIdAndBought(userId, StatusConstant.NOT_BOUGHT);
    }

    private CartResponse convertCartToCartResponse(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setPrice(cart.getPrice());
        cartResponse.setProductId(cart.getProductId());
        cartResponse.setProductName(cart.getProductName());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setImage(cart.getImage());
        cartResponse.setUserId(cart.getUserId());
        return cartResponse;
    }
}
