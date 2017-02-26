package com.tutoring.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.annotations.InvalidMessage;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Message;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.service.MessageService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.Mappings;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;

@RestController
@RequestMapping(Mappings.MESSAGE)
public class MessageController{
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private LessonService lessonService;
	
	@InvalidMessage
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseVO sendMessage(@RequestBody Message message, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Lesson lesson = lessonService.getLessonsByLessonId(message.getLesson().getId());
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			message.setSenderProfile(currentProfile);
			
			//if sender id  equals student id than message is generated for tutor 
			if(currentProfile.getId() == lesson.getStudentProfile().getId())
				message.setReceiverProfile(lesson.getTutorProfile());
			else   
				message.setReceiverProfile(lesson.getStudentProfile());
			
			message.setLesson(lesson);
			message.setCreatedBy(currentProfile.getEmail());
			responseVO = messageService.save(message);
			if(Objects.isNull(responseVO)) {
				responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.message.send.error"));
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}
	
	@RequestMapping(value = "/{lessonId}", method = RequestMethod.GET)
	public ResponseVO getMessageByLessonId(@PathVariable("lessonId") long lessonId, @RequestBody Message message, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			List<Message> messages = messageService.getMessageByLessonId(lessonId);
			responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, AppConstants.SPACE, messages, null);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}
}
