package com.tutoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.util.AppConstants;
import com.tutoring.util.ResponseVO;


@RestController
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(AppException.class)
	public ResponseVO exception(AppException e, HttpServletRequest request, HttpServletResponse response) {
		ResponseVO responseVO = new ResponseVO(AppConstants.ERROR,
				AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
		if(response.getStatus()==HttpServletResponse.SC_OK) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}else{
			responseVO.setMessage(e.getMessage());
		}
		return responseVO;
	}
	
}
