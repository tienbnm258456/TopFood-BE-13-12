package com.demo.shop.request;

import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SalesReportRequest {
	
	@ApiModelProperty(value = "hình thức truy vấn: C = tùy chọn, T = hôm nay, Y = hôm qua, L7 = 7 ngày sau,"
			+ "										 TW = Tuần này, LW = Tuần sau, TM = Tháng này, LM = Tháng sau,"
			+ "										 6M = 6 Tháng gần nhất, 12M = 12 tháng gần nhất, A = Tất cả", allowableValues = "C,T,Y,TW,LW,TM,LM,6M,12M,A")
	private String type;
	
	private Date fromTime;
	
	private Date toTime;

}
