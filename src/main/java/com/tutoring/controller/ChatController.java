package com.tutoring.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.annotations.InvalidMessage;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Message;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.service.MessageService;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;


@RestController
public class ChatController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private ProfileService profileService;
	
	@InvalidMessage
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public ResponseVO sendMessage(Message message) throws AppException {
		ResponseVO responseVO = null;
		try {
			Lesson lesson = lessonService.getLessonsByLessonId(message.getLesson().getId());
			Profile currentProfile = profileService.getProfile(message.getCurrentProfile());
			message.setSenderProfile(currentProfile);

			//if sender id  equals student id than message is generated for tutor 
			if(currentProfile.getId() == lesson.getStudentProfile().getId()) {
				Profile tutorProfile = lesson.getTutorProfile();
				if(Objects.nonNull(tutorProfile)) 
					message.setReceiverProfile(tutorProfile);
			}
			else {   
				message.setReceiverProfile(lesson.getStudentProfile());
			}

			message.setLesson(lesson);
			message.setCreatedBy(currentProfile.getEmail());
			responseVO = messageService.save(message);
			if(Objects.isNull(responseVO)) {
				responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.message.send.error"));
			}
		} catch (Exception e) {
			//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}
}
