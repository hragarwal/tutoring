package com.tutoring.service;

import java.util.List;

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
	
	/**
	 * Fetch all message for lesson.
	 * @param lessonUniqueId - lesson unique id
	 * @return list of message details
	 * @throws AppException
	 */
	public List<Message> getMessageByLessonUniqueId(String lessonUniqueId) throws AppException;
}
