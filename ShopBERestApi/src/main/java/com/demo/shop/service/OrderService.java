package com.demo.shop.service;

import com.demo.shop.response.RevenueResponse;
import com.demo.shop.request.OrderLocalRequest;
import com.demo.shop.request.OrderRequest;
import com.demo.shop.response.OrderResponse;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    String createPayment(HttpServletRequest httpRequest, OrderRequest orderRequest) throws PayPalRESTException;

    String executePayment(Integer userId, String paymentId, String payerId) throws PayPalRESTException;

    OrderResponse createPaymentLocal(OrderLocalRequest request);

    RevenueResponse revenuaByDay();

    RevenueResponse revenuaByWeek();

    RevenueResponse revenuaByMonth();

    RevenueResponse revenuaByYear();

    List<OrderResponse> getListOrderByDay();

    List<OrderResponse> getListOrderByWeek();

    List<OrderResponse> getListOrderByMonth();

    List<OrderResponse> getListOrderByYear();
}
