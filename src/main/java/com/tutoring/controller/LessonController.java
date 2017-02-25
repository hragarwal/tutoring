package com.tutoring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@RestController
public class LessonController extends AppController {

	@Autowired
	private LessonService lessonService;

	@RequestMapping(value = Mappings.NEW_LESSON, method = RequestMethod.POST)
	public ResponseVO createLesson(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile studentProfile = AppUtils.getCurrentUserProfile(request);
			lesson.setStudentProfile(studentProfile);
			responseVO = lessonService.createLesson(lesson);
		} catch (Exception e) {
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.FETCH_LESSONS_BY_PROFILE, method = RequestMethod.POST)
	public ResponseVO getLessonByStudentProfile(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(request);
			List<Lesson> lessons = lessonService.getLessonsByProfile(profile.getId());
			responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_ERROR, AppConstants.SPACE,
					lessons, null);
		} catch (Exception e) {
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			throw new AppException(e);
		}
		return responseVO;
	}

	/*@RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateLesson(@RequestBody Lesson lesson){

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Lesson> getAllLesson(){
        return null;
    }*/
}
