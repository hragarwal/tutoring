package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.util.ResponseVO;

public interface MessageService {

	
	/**
	 * Save the user message.
	 * @param message details
	 * @return response details
	 * @throws AppException
	 */
	public ResponseVO save(Message message) throws AppException;
}
