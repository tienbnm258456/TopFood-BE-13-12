package com.demo.shop.service;

import java.util.List;

import com.demo.shop.request.SalesReportRequest;
import com.demo.shop.response.SalesReportResponse;

public interface SalesReportService {

	public List<SalesReportResponse> salesReport(SalesReportRequest request);
}
