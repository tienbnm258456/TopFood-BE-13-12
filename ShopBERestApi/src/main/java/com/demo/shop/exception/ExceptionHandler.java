package com.demo.shop.exception;

public class ExceptionHandler extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionHandler(String status) {
        super(status);
    }
}
