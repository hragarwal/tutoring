package com.tutoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.annotations.InvalidMessage;
import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

@RestController
public class MessageController extends AppController {
	
	@InvalidMessage
	@RequestMapping(value = Mappings.SEND_MESSAGE, method = RequestMethod.POST)
	public ResponseVO sendMessage(@RequestBody Message message, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
		} catch (Exception e) {
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			throw new AppException("Exception occurred while executing method validateUser with input ");
		}
		return responseVO;
	}
}
