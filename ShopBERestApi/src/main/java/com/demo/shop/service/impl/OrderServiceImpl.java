package com.demo.shop.service.impl;

import com.demo.shop.config.PaypalPaymentIntent;
import com.demo.shop.config.PaypalPaymentMethod;
import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Order;
import com.demo.shop.entity.OrderDetail;
import com.demo.shop.entity.Product;
import com.demo.shop.repository.CartRepository;
import com.demo.shop.repository.OrderDetailRepository;
import com.demo.shop.repository.OrderRepository;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.request.OrderDetailRequest;
import com.demo.shop.request.OrderLocalRequest;
import com.demo.shop.request.OrderRequest;
import com.demo.shop.response.OrderResponse;
import com.demo.shop.response.RevenueResponse;
import com.demo.shop.service.EmailService;
import com.demo.shop.service.OrderService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	final OrderRepository orderRepository;

	final OrderDetailRepository orderDetailRepository;

	final CartRepository cartRepository;

	final ProductRepository productRepository;

	final EmailService emailService;

	private final APIContext apiContext;

	public OrderServiceImpl(APIContext apiContext, OrderDetailRepository orderDetailRepository,
			OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository,
			EmailService emailService) {
		this.apiContext = apiContext;
		this.orderDetailRepository = orderDetailRepository;
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.emailService = emailService;
	}

	@Override
	@Transactional
	public String createPayment(HttpServletRequest httpRequest, OrderRequest orderRequest) throws PayPalRESTException {

		Double price = Double.valueOf(orderRequest.getPrice()) / 23000;
		Payment payment = create(price, orderRequest.getDescription(), orderRequest.getCancelUrl(),
				orderRequest.getSuccessUrl());
		if (payment != null) {
			Order order = new Order();
			order.setStatus(StatusConstant.ORDER_PENDING);
			order.setCreatedDate(new Date());
			order.setDescription(orderRequest.getDescription());
			order.setTotalPrice(orderRequest.getPrice());
			order.setUserId(orderRequest.getUserId());
			order.setPaymentId(payment.getId());
			Order orderSave = orderRepository.saveAndFlush(order);
			List<OrderDetail> orderDetails = new ArrayList<>();
			if (!orderRequest.getOrderDetailRequests().isEmpty()) {
				for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequests()) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOrderId(orderSave.getId());
					orderDetail.setProductId(orderDetailRequest.getProductId());
					orderDetail.setQuantity(orderDetailRequest.getQuantity());
					orderDetails.add(orderDetail);
				}
				orderDetailRepository.saveAll(orderDetails);
			}
			for (Links links : payment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return links.getHref();
				}
			}
		}

		throw new PayPalRESTException("create payment fail");
	}

	private Payment create(Double price, String description, String cancelUrl, String successUrl)
			throws PayPalRESTException {

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.format("%.2f", price));

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(PaypalPaymentMethod.paypal.toString());

		Payment payment = new Payment();
		payment.setIntent(PaypalPaymentIntent.sale.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		apiContext.setMaskRequestId(true);
		return payment.create(apiContext);
	}

	@Override
	@Transactional
	public String executePayment(Integer userId, String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		Payment response = payment.execute(apiContext, paymentExecute);
		Order order = orderRepository.findByUserIdAndPaymentId(userId, paymentId);
		order.setUpdatedDate(new Date());
		if (response.getState().equals("approved")) {
			order.setStatus(StatusConstant.ORDER_SUCCESS);
			orderRepository.save(order);
			cartRepository.updateBoughtByUserId(userId);
			return "success";
		}
		order.setStatus(StatusConstant.ORDER_ERROR);
		orderRepository.save(order);
		throw new PayPalRESTException("approved false");
	}

	@Override
	@Transactional
	public OrderResponse createPaymentLocal(OrderLocalRequest request) {
		Order order = new Order();
		order.setStatus(StatusConstant.ORDER_PENDING);
		order.setCreatedDate(new Date());
		order.setDescription(request.getDescription());
		order.setTotalPrice(request.getPrice());
		order.setUserId(request.getUserId());
		order.setAddress(request.getAddress());
		order.setPhone(request.getPhone());
		order.setName(request.getName());
		Order orderSave = orderRepository.saveAndFlush(order);
		List<OrderDetail> orderDetails = new ArrayList<>();
		if (!request.getOrderDetailRequests().isEmpty()) {
			for (OrderDetailRequest orderDetailRequest : request.getOrderDetailRequests()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(orderSave.getId());
				orderDetail.setProductId(orderDetailRequest.getProductId());
				orderDetail.setQuantity(orderDetailRequest.getQuantity());
				orderDetails.add(orderDetail);
				Product product = productRepository
						.findByIdAndStatus(orderDetailRequest.getProductId(), StatusConstant.STATUS_ACTIVE).get();
				productRepository.updateQuantity(product.getQuantity() - orderDetailRequest.getQuantity(),
						orderDetailRequest.getProductId());
			}
			orderDetailRepository.saveAll(orderDetails);
		}
		cartRepository.updateBoughtByUserId(request.getUserId());
		return convertOrderToOrderResponse(orderSave);
	}

	public OrderResponse convertOrderToOrderResponse(Order order) {
		if (order == null) {
			return null;
		}
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setAddress(order.getAddress());
		orderResponse.setId(order.getId());
		orderResponse.setDescription(order.getDescription());
		orderResponse.setPhone(order.getPhone());
		orderResponse.setStatus(order.getStatus());
		orderResponse.setTotalPrice(order.getTotalPrice());
		orderResponse.setName(order.getName());
		return orderResponse;
	}

	@Override
	public RevenueResponse revenuaByDay() {
		RevenueResponse revenueResponse = new RevenueResponse();
		Integer revenuaByDay = orderRepository.revenueByDay();
		revenueResponse.setRevenueByDay(revenuaByDay);
		return revenueResponse;
	}

	@Override
	public RevenueResponse revenuaByWeek() {
		RevenueResponse revenueResponse = new RevenueResponse();
		Integer revenuaByWeek = orderRepository.revenueByWeek();
		revenueResponse.setRevenueByWeek(revenuaByWeek);
		return revenueResponse;
	}

	@Override
	public RevenueResponse revenuaByMonth() {
		RevenueResponse revenueResponse = new RevenueResponse();
		Integer revenuaByMonth = orderRepository.revenueByMonth();
		revenueResponse.setRevenueByMonth(revenuaByMonth);
		return revenueResponse;
	}

	@Override
	public RevenueResponse revenuaByYear() {
		RevenueResponse revenueResponse = new RevenueResponse();
		Integer revenuaByYear = orderRepository.revenueByYear();
		revenueResponse.setRevenueByYear(revenuaByYear);
		return revenueResponse;
	}

	@Override
	public List<OrderResponse> getListOrderByDay() {
		List<Order> orders = orderRepository.getListOrderByDay();
		List<OrderResponse> orderResponses = new ArrayList<>();
		for (Order order : orders) {
			OrderResponse response = new OrderResponse();
			response.setId(order.getId());
			response.setAddress(order.getAddress());
			response.setDescription(order.getDescription());
			response.setName(order.getName());
			response.setPhone(order.getPhone());
			response.setTotalPrice(order.getTotalPrice());
			response.setUserId(order.getUserId());
			orderResponses.add(response);
		}
		return orderResponses;
	}

	@Override
	public List<OrderResponse> getListOrderByWeek() {
		List<Order> orders = orderRepository.getListOrderByWeek();
		List<OrderResponse> orderResponses = new ArrayList<>();
		for (Order order : orders) {
			OrderResponse response = new OrderResponse();
			response.setId(order.getId());
			response.setAddress(order.getAddress());
			response.setDescription(order.getDescription());
			response.setName(order.getName());
			response.setPhone(order.getPhone());
			response.setTotalPrice(order.getTotalPrice());
			response.setUserId(order.getUserId());
			orderResponses.add(response);
		}
		return orderResponses;
	}

	@Override
	public List<OrderResponse> getListOrderByMonth() {
		List<Order> orders = orderRepository.getListOrderByMonth();
		List<OrderResponse> orderResponses = new ArrayList<>();
		for (Order order : orders) {
			OrderResponse response = new OrderResponse();
			response.setId(order.getId());
			response.setAddress(order.getAddress());
			response.setDescription(order.getDescription());
			response.setName(order.getName());
			response.setPhone(order.getPhone());
			response.setTotalPrice(order.getTotalPrice());
			response.setUserId(order.getUserId());
			orderResponses.add(response);
		}
		return orderResponses;
	}

	@Override
	public List<OrderResponse> getListOrderByYear() {
		List<Order> orders = orderRepository.getListOrderByYear();
		List<OrderResponse> orderResponses = new ArrayList<>();
		for (Order order : orders) {
			OrderResponse response = new OrderResponse();
			response.setId(order.getId());
			response.setAddress(order.getAddress());
			response.setDescription(order.getDescription());
			response.setName(order.getName());
			response.setPhone(order.getPhone());
			response.setTotalPrice(order.getTotalPrice());
			response.setUserId(order.getUserId());
			orderResponses.add(response);
		}
		return orderResponses;
	}
}
