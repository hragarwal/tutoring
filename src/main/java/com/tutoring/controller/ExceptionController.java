package com.tutoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tutoring.exception.AppException;


@Controller
@ControllerAdvice
public class ExceptionController {
	
	
	@ExceptionHandler(AppException.class)
	public String exception(AppException e) {
		/*ModelAndView mav = new ModelAndView("error");
		mav.addObject("errorname", e.getClass().getSimpleName());
		mav.addObject("errormessage", e.getMessage());
		mav.addObject("exception", e.getNestedException());*/
		return e.getMessage();
	}

	
}
