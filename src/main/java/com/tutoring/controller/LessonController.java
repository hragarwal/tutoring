package com.tutoring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.annotations.LessonAuthorize;
import com.tutoring.exception.AppException;
import com.tutoring.model.Audit;
import com.tutoring.model.AuditContent;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.AuditService;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.LessonStates;
import com.tutoring.util.Mappings;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;
import com.tutoring.util.RoleStates;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@RestController
@RequestMapping(Mappings.LESSON)
public class LessonController {

	@Autowired
	private LessonService lessonService;

	@Autowired 
	private AuditService auditService;

	private Logger logger = LoggerFactory.getLogger(LessonController.class);


	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseVO createLesson(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			//make sure only student are submitting lesson for now
			Profile studentProfile = AppUtils.getCurrentUserProfile(request);
			lesson.setStudentProfile(studentProfile);
			lesson.setCreatedBy(studentProfile.getUsername());
			boolean isSuccess = lessonService.createLesson(lesson,studentProfile);
			if(isSuccess) {
				List<Lesson> lessons = lessonService.getLessonsByProfile(studentProfile.getId());
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.lesson.create.success"),
						lessons, null);
				saveAuditInfo(studentProfile, lesson, 0);
			} else {
				responseVO = new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_ERROR, MessageReader.READER.getProperty("api.message.lesson.create.error"));
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	@LessonAuthorize // - not required as we are fetching profile for that particular user only from current session
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ResponseVO getLessonByStudentProfile(HttpServletRequest request, HttpServletResponse response) throws AppException {
		try {
			Profile profile = AppUtils.getCurrentUserProfile(request);
			List<Lesson> lessons = lessonService.getLessonsByProfile(profile.getId());
			return new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_ERROR, AppConstants.SPACE, lessons, null);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@LessonAuthorize
	@RequestMapping(value = "/{lessonId}", method = RequestMethod.GET)
	public ResponseVO getLesson(@PathVariable("lessonId") long lessonId, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			Lesson lesson2 = lessonService.getLessonsByLessonId(lessonId);
			responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, AppConstants.SPACE,
					lesson2, null);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	// this method only for tutor profile to list all available lesson
	@RequestMapping(value = Mappings.LESSON_AVAILABLE, method = RequestMethod.GET)
	public ResponseVO getLesson(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.SUPER_ADMIN |
					RoleStates.ADMIN | RoleStates.TUTOR)) {
				List<Lesson> lessons = lessonService.getAvailableLessons(LessonStates.AVAILABLE);
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, AppConstants.SPACE, lessons, null);
			} else {
				responseVO = new ResponseVO(HttpServletResponse.SC_UNAUTHORIZED, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.unauthorized.data.error"));
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	/* Currently we are not using this method */
	@RequestMapping(value = Mappings.LESSON_BY_STATUS, method = RequestMethod.GET)
	public ResponseVO getLessonByStatus(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.SUPER_ADMIN |
					RoleStates.ADMIN | RoleStates.TUTOR)) {
				List<Lesson> lessons = lessonService.getAvailableLessons(LessonStates.AVAILABLE);
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, AppConstants.SPACE, lessons, null);
			} 
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	// might be we may need to use lessonAuthorize annotations
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseVO updateLessonStatus(@RequestBody Lesson lesson, 
			HttpServletRequest request, HttpServletResponse response)  throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			// check if user is allowed to take the action
			if(!AppUtils.isAccessible(currentProfile.getRole().getId(), lesson.getStatus().getId())) {
				responseVO = new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.lesson.notallowed.action"));
			}
			else {
				responseVO = lessonService.updateLessonStatus(lesson, currentProfile);
				// only if call is success save audit data
				if(responseVO.getStatus() == HttpServletResponse.SC_OK) {
					saveAuditInfo(currentProfile, (Lesson)responseVO.getData(), Long.valueOf(responseVO.getAccessToken()));
				}
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	/**
	 * This method saves the audit info whenever lesson status is updated.
	 * Note : Do not throw the exception from this method.
	 * @param profile - current user profile
	 * @param lesson- new lesson details after successful update
	 * @param oldLessonStatus - old status of lesson
	 */
	private void saveAuditInfo(Profile profile, Lesson lesson, long oldLessonStatus) {
		List<AuditContent> auditContents = new ArrayList<>();
		Audit audit = new Audit();
		audit.setCreatedBy(profile.getUsername());
		audit.setLessonId(lesson.getId());
		if(lesson.getStatus().getId() == LessonStates.AVAILABLE) {
			auditContents.add(new AuditContent("Lesson Status", "", "Requested", ""));
		} else {
			auditContents.add(new AuditContent("Lesson Status", LessonStates.getAllLessonStates().get(oldLessonStatus), 
					LessonStates.getAllLessonStates().get(lesson.getStatus().getId()), ""));
		}
		try {
			audit.setContent(AppUtils.getJSONValue(auditContents));
			auditService.save(audit);
		} catch (AppException | JsonProcessingException e) {
			logger.error("An exception occured while adding info to audit table :", e);
		}
	}
}
