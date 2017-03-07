package com.tutoring.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.LessonStatus;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

/**
 * Controller to work with all kind of status in application. 
 * @author CHIRAG 
 */
@RestController
public class StausController {
	
	@Autowired
	private LessonStatusDAO lessonStatusDAO;

	@RequestMapping(value = Mappings.GET_ALL_LESSON_STATUS, method = RequestMethod.GET)
	public ResponseVO fetchLessonStatus(HttpServletRequest request, HttpServletResponse response) throws AppException {
		try {
			Iterator<LessonStatus> lessonStatus= lessonStatusDAO.findAll().iterator();
			List<LessonStatus> lessonStatusList =  IteratorUtils.toList(lessonStatus);
			return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE,AppConstants.BLANK, lessonStatusList, null);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException("Exception occurred while executing method fetchLessonStatus ", e);
		}
	}
	
}
