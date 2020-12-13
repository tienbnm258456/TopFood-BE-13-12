package com.demo.shop.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.shop.constant.SalesReportConstant;
import com.demo.shop.repository.SalesReportRepository;
import com.demo.shop.request.SalesReportRequest;
import com.demo.shop.response.SalesReportData;
import com.demo.shop.response.SalesReportResponse;
import com.demo.shop.service.SalesReportService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesReportServiceImpl implements SalesReportService {

	@Autowired
	private SalesReportRepository salesReportRepository;

	@Override
	public List<SalesReportResponse> salesReport(SalesReportRequest request) {

//		SalesReportDTO response = new SalesReportDTO();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		LocalDateTime now = LocalDateTime.now();
		// start of a day
		LocalDateTime startOfDay = now.with(LocalTime.MIN);
		// end of a day
		LocalDateTime endOfDay = now.with(LocalTime.MAX);

		// check fromTime and toTime
		Date fromTime = new Date();
		Date toTime = new Date();

		switch (request.getType()) {
		case SalesReportConstant.TODAY:
			fromTime = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
			toTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
			break;
		case SalesReportConstant.YESTERDAY:
			LocalDateTime yesterday = now.minusDays(1);
			fromTime = Date.from(yesterday.with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
			toTime = Date.from(yesterday.with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
			break;
		case SalesReportConstant.LAST_SEVEN:
			LocalDateTime lastSevenDay = now.minusDays(7);
			fromTime = Date.from(lastSevenDay.with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
			toTime = Date.from(endOfDay.with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
			break;
		case SalesReportConstant.THIS_WEEK:
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			fromTime = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			toTime = calendar.getTime();
			break;
		case SalesReportConstant.LAST_WEEK:
			calendar.add(Calendar.WEEK_OF_YEAR, -1);
			// lấy ngày đầu và cuối tuần trước
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			fromTime = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			toTime = calendar.getTime();
			break;
		case SalesReportConstant.THIS_MONTH:
			// lấy ngày đầu và cuối tháng
			calendar.set(Calendar.DATE, 1);
			fromTime = calendar.getTime();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			toTime = calendar.getTime();
			break;
		case SalesReportConstant.LAST_MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			fromTime = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			toTime = calendar.getTime();
			break;
		case SalesReportConstant.SIX_MONTH:
			LocalDateTime lastSixMonth = now.minusMonths(6);
			fromTime = Date.from(lastSixMonth.with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
			toTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
			break;
		case SalesReportConstant.TWELVE_MONTH:
			LocalDateTime lastTwelveMonth = now.minusMonths(12);
			fromTime = Date.from(lastTwelveMonth.with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
			toTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
			break;
		case SalesReportConstant.CHOSE:
			if (StringUtils.isEmpty(request.getFromTime()) && StringUtils.isEmpty(request.getToTime())) {
				log.error("Không hợp lệ");
			}
			fromTime = request.getFromTime();
			toTime = request.getToTime();
			break;

		case SalesReportConstant.ALL:
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateInString = "01/01/2010";
			try {
				fromTime = formatter.parse(dateInString);
			} catch (ParseException e) {
				log.error("Không hợp lệ");
				e.printStackTrace();
			}
			toTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
			break;

		default:
			log.error("Không hợp lệ");
		}
		// chuyển thời gian về 00:00:00
		calendar.setTime(fromTime);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		fromTime = calendar.getTime();
		// chuyển thời gian về 23:59:59
		calendar.setTime(toTime);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		toTime = calendar.getTime();

		List<SalesReportData> sales = salesReportRepository.querySalesReport(fromTime, toTime);
		if (sales.isEmpty()) {
			log.error("Không có dữ liệu");
		}
		
		List<SalesReportResponse> salesDTOs = new ArrayList<>();
		salesDTOs = sales.stream().map(s -> {

			SalesReportResponse saleDTO = new SalesReportResponse();
			saleDTO.setId(s.getProductId());
			saleDTO.setProductName(s.getProductName());
			saleDTO.setTotalPrice(s.getTotalPrice());
			saleDTO.setCountQuantity(s.getCountQuantity());

			return saleDTO;

		}).filter(s -> s != null).collect(Collectors.toList());

		Integer totalSales = 0;
		SalesReportResponse totals = new SalesReportResponse();
		for(SalesReportResponse saleDTO : salesDTOs) {
			Integer totalPrice = saleDTO.getTotalPrice();
			
			totalSales += totalPrice;
			totals.setTotalSales(totalSales);
		}
		salesDTOs.add(totals);
//		response.setSalesDTOs(salesDTOs);

		return salesDTOs;
	}

}
