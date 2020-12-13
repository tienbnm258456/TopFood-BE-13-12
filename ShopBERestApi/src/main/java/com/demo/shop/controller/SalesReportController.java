package com.demo.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.request.SalesReportRequest;
import com.demo.shop.response.SalesReportResponse;
import com.demo.shop.service.SalesReportService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SalesReportController {

	@Autowired
    SalesReportService salesReportService;

    @PostMapping("/sales-report")
    public ResponseEntity<?> getSalesReport(@RequestBody SalesReportRequest request) {
    	 List<SalesReportResponse> response = salesReportService.salesReport(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
