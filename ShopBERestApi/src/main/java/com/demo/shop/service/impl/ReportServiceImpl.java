package com.demo.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.demo.shop.constant.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.Report;
import com.demo.shop.repository.ReportReponsitory;
import com.demo.shop.request.ReportRequest;
import com.demo.shop.response.ReportResponse;
import com.demo.shop.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportReponsitory reportReponsitory;

	@Override
	public List<ReportResponse> getAll() {
		List<Report> reports = reportReponsitory.findByStatus(StatusConstant.STATUS_ACTIVE);
		List<ReportResponse> reportResponses = new ArrayList<>();
		for (Report report : reports) {
			ReportResponse response = new ReportResponse();
			response.setName(report.getName());
			response.setPhone(report.getPhone());
			response.setEmail(report.getEmail());
			response.setProblem(report.getProblem());
			response.setReply(report.getReply());
			response.setStatus(report.getStatus());
			response.setCreateDate(report.getCreateDate());
			response.setUpdateAt(report.getUpdateAt());
			response.setId(report.getId());
			reportResponses.add(response);
		}
		return reportResponses;
	}

	@Override
	public ReportResponse getById(Integer id) {
		ReportResponse response = new ReportResponse();
		Optional<Report> report = reportReponsitory.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE);
		if (report != null) {
			response.setName(report.get().getName());
			response.setPhone(report.get().getPhone());
			response.setEmail(report.get().getEmail());
			response.setProblem(report.get().getProblem());
			response.setReply(report.get().getReply());
			response.setStatus(report.get().getStatus());
			response.setCreateDate(report.get().getCreateDate());
			response.setUpdateAt(report.get().getUpdateAt());
			response.setId(report.get().getId());
		}
		return response;
	}

	@Override
	public Report add(ReportRequest reportRequest) {
		Report report = new Report();
		report.setName(reportRequest.getName());
		report.setPhone(reportRequest.getPhone());
		report.setEmail(reportRequest.getEmail());
		report.setProblem(reportRequest.getProblem());
		report.setReply(reportRequest.getReply());
		report.setStatus(StatusConstant.STATUS_ACTIVE);
		report.setCreateDate(new Date());
		return reportReponsitory.save(report);

	}

	@Override
	public Report update(Integer id, ReportRequest reportRequest) {
		Report report = reportReponsitory.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("Category with not found id  = " + id));
		report.setName(reportRequest.getName());
		report.setPhone(reportRequest.getPhone());
		report.setEmail(reportRequest.getEmail());
		report.setProblem(reportRequest.getProblem());
		report.setUpdateAt(new Date());
		return reportReponsitory.save(report);

	}

	@Override
	public Report deletel(Integer id) {
		Report report = reportReponsitory.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with not found id  = " + id));
		report.setStatus(StatusConstant.STATUS_INACTIVE);
		return reportReponsitory.save(report);
	}

}
