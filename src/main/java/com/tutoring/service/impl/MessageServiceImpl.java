package com.tutoring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tutoring.dao.MessageDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.service.MessageService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;

public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;
	
	@Override
	public ResponseVO save(Message message) throws AppException {
		message = messageDAO.save(message);
		return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.message.send.success"), message, null);
	}

	@Override
	public List<Message> getMessageByLessonId(long lessonId) throws AppException {
		return messageDAO.getMessagesByLessonId(lessonId);
	}

}
