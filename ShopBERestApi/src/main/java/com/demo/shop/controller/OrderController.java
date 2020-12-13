package com.demo.shop.controller;

import com.demo.shop.request.OrderLocalRequest;
import com.demo.shop.request.OrderRequest;
import com.demo.shop.response.OrderResponse;
import com.demo.shop.service.OrderService;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("payment")
public class OrderController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<String> payment(HttpServletRequest httpRequest,
                                          @RequestBody @Valid OrderRequest request) {
        try {
            return ResponseEntity.ok().body(orderService.createPayment(httpRequest, request));
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Payment error");
        }
    }

    @GetMapping
    public ResponseEntity<String> successPay(@RequestParam("userId") Integer userId,
                                             @RequestParam("paymentId") String paymentId,
                                             @RequestParam("PayerID") String payerId){
        try {
            return ResponseEntity.ok().body(orderService.executePayment(userId, paymentId, payerId));
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Payment approve error");
        }
    }

    @PostMapping("local")
    public ResponseEntity<OrderResponse> orderLocal(@RequestBody OrderLocalRequest request) {
        return ResponseEntity.ok().body(orderService.createPaymentLocal(request));
    }
}
