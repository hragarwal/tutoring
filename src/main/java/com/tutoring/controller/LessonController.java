package com.tutoring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.annotations.LessonAuthorize;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.Mappings;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@RestController
@RequestMapping(Mappings.LESSON)
public class LessonController {

	@Autowired
	private LessonService lessonService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseVO createLesson(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			//make sure only student are submitting lesson for now
			Profile studentProfile = AppUtils.getCurrentUserProfile(request);
			lesson.setStudentProfile(studentProfile);
			lesson.setCreatedBy(studentProfile.getEmail());
			boolean isSuccess = lessonService.createLesson(lesson);
			if(isSuccess) {
				List<Lesson> lessons = lessonService.getLessonsByProfile(studentProfile.getId());
				responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.lesson.create.success"),
						lessons, null);
			} else {
				responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, MessageReader.READER.getProperty("api.message.lesson.create.error"));
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

	//@LessonAuthorize - not required as we are fetching profile for that particular user only from current session
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ResponseVO getLessonByStudentProfile(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(request);
			List<Lesson> lessons = lessonService.getLessonsByProfile(profile.getId());
			responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_ERROR, AppConstants.SPACE,
					lessons, null);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}
	
	@LessonAuthorize
	@RequestMapping(value = "/{lessonId}", method = RequestMethod.GET)
    public ResponseVO getLesson(@PathVariable("lessonId") long lessonId, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			Lesson lesson2 = lessonService.getLessonsByLessonId(lessonId);
			responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_ERROR, AppConstants.SPACE,
					lesson2, null);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
    }

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Lesson> getAllLesson(){
        return null;
    }*/
}
