package com.tutoring.controller;

import com.tutoring.exception.AppException;
import com.tutoring.util.AppConstants;
import com.tutoring.util.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Controller
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(AppException.class)
	public ResponseVO exception(AppException e) {
		ResponseVO responseVO = new ResponseVO(AppConstants.ERROR,
				AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
		return responseVO;
	}

	
}
