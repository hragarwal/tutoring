package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.model.dto.MessageDto;
import com.tutoring.util.ResponseVO;

import java.util.List;

public interface MessageService {

	
	/**
	 * Save the user message.
	 * @param message details
	 * @return response details
	 * @throws AppException
	 */
	public ResponseVO save(Message message) throws AppException;
	
	/**
	 * Fetch all message for lesson.
	 * @param lessonUniqueId - lesson unique id
	 * @return list of message details
	 * @throws AppException
	 */
	public List<MessageDto> getMessageByLessonUniqueId(String lessonUniqueId) throws AppException;
}
