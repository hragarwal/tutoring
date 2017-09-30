package com.tutoring.service.impl;

import com.tutoring.dao.MessageDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.service.LessonService;
import com.tutoring.service.MessageService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.LessonStates;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private LessonService lessonService;
	
	@Override
	public ResponseVO save(Message message) throws AppException {
		message = messageDAO.save(message);
		return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.message.send.success"), message, null);
	}

	@Override
	public List<Message> getMessageByLessonId(long lessonId) throws AppException {
		if(lessonService.getLessonStatus(lessonId).getId()== LessonStates.AVAILABLE){
			return messageDAO.getMessagesByLessonIdAndAvailableStatus(lessonId);
		}
		return messageDAO.getMessagesByLessonId(lessonId);
	}

}
