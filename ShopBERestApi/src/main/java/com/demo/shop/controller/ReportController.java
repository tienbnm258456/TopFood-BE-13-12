package com.demo.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.Report;
import com.demo.shop.request.ReportRequest;
import com.demo.shop.response.ReportResponse;
import com.demo.shop.service.ReportService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping("/report")
	public ResponseEntity<?> getAll(){
		List<ReportResponse> reports = reportService.getAll();
		return new ResponseEntity<>(reports, HttpStatus.OK);
	}
	@GetMapping("/report/{id}")
	public ResponseEntity<ReportResponse> getById(@PathVariable(value = "id") Integer id){
		return ResponseEntity.ok().body(reportService.getById(id));
	
	}
	@PostMapping("/report")
	public ResponseEntity<Report> create(@RequestBody ReportRequest reportRequest){
		return ResponseEntity.ok().body(reportService.add(reportRequest));
	
	}
	@PutMapping("/report/{id}")
	public ResponseEntity<Report> update(@RequestBody ReportRequest reportRequest, @PathVariable(value = "id") Integer id){
		return ResponseEntity.ok().body(reportService.update(id, reportRequest));
	
	}
	@DeleteMapping("/report/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id){
		reportService.deletel(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
