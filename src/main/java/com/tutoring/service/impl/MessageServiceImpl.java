package com.tutoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tutoring.model.dto.MessageDto;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.MessageDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Message;
import com.tutoring.service.LessonService;
import com.tutoring.service.MessageService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.LessonStates;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;
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
		List<Message> messages = new ArrayList<>(1);
		messages.add(message);
		return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.message.send.success"), buildMessageResponse(messages).get(0), null);
	}

	@Override
	public List<MessageDto> getMessageByLessonUniqueId(String lessonUniqueId) throws AppException {
		List<Message> messages = null;
		if(lessonService.getLessonsByUniqueId(lessonUniqueId).getStatus().getId()== LessonStates.AVAILABLE){
			messages = messageDAO.getMessagesByLessonUniqueIdAndAvailableStatus(lessonUniqueId);
		} else {
			messages = messageDAO.getMessagesByLessonUniqueId(lessonUniqueId);
		}
		return buildMessageResponse(messages);
	}


	private List<MessageDto> buildMessageResponse(List<Message> messages) {
		List<MessageDto> messageDtoList = new ArrayList<>();
		if(Objects.nonNull(messages)) {
			messages.forEach(message ->  {
				messageDtoList.add(new MessageDto(message)); });
		}
		return messageDtoList;
	}
}
