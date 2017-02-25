package com.tutoring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Subject;
import com.tutoring.service.SubjectService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

@RestController
public class SubjectController extends AppController {

	
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(value = Mappings.FETCH_SUBJECTS, method = RequestMethod.POST)
	public ResponseVO fetchSubjects(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			List<Subject> subjects = subjectService.getAllSubjects();
			responseVO  = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE,AppConstants.BLANK, subjects, null);
		} catch (Exception e) {
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			throw new AppException("Exception occurred while executing method fetchSubjects ", e);
		}
		return responseVO;
	}
	
}
