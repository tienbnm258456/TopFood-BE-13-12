package com.demo.shop.controller;

import java.util.List;

import com.demo.shop.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.shop.response.CartResponse;
import com.demo.shop.service.CartService;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAll(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok().body(cartService.findAllByUserId(userId));
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cartId") Integer cartId) {
        cartService.delete(cartId);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody @Valid CartRequest cartRequest) {
        return ResponseEntity.ok().body(cartService.create(cartRequest));
    }

    @GetMapping("count")
    public ResponseEntity<Integer> countCartByUser(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok().body(cartService.countCartByUser(userId));
    }
}
