package com.demo.shop.service;

import java.util.List;

import com.demo.shop.entity.Report;
import com.demo.shop.request.ReportRequest;
import com.demo.shop.response.ReportResponse;

public interface ReportService {
	
	public List<ReportResponse> getAll();
	
	public ReportResponse getById(Integer id);
	
	public Report add(ReportRequest reportRequest);
	
	public Report update(Integer id, ReportRequest reportRequest);
	
	public Report deletel(Integer id);

}
