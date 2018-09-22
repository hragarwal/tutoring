package com.tutoring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.annotations.LessonAuthorize;
import com.tutoring.exception.AppException;
import com.tutoring.model.Audit;
import com.tutoring.model.AuditContent;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.model.dto.LessonDto;
import com.tutoring.service.AuditService;
import com.tutoring.service.LessonService;
import com.tutoring.service.PushNotificationService;
import com.tutoring.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Autowired
	private PushNotificationService notificationService;

	private Logger logger = LoggerFactory.getLogger(LessonController.class);

	@Value("${file.save.location.profile}")
	private String profileDirectory;


	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseVO createLesson(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			//make sure only student are submitting lesson for now
			Profile studentProfile = AppUtils.getCurrentUserProfile(request);
			lesson.setStudentProfile(studentProfile);
			lesson.setCreatedBy(studentProfile.getUsername());
			boolean isSuccess = lessonService.createLesson(lesson,studentProfile);
			if(isSuccess) {
				AppUtils.deleteDirectoryForUser(studentProfile, profileDirectory);
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.lesson.create.success"),
						lesson, null);
				saveAuditInfo(studentProfile, lesson, 0);
				notificationService.notifyUsers(lesson);
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
			List<LessonDto> lessons = lessonService.getLessonsByProfile(profile.getId());
			return new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_ERROR, AppConstants.SPACE, lessons, null);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}


	@LessonAuthorize
	@RequestMapping(value = "/{lessonUniqueId}", method = RequestMethod.GET)
	public ResponseVO getLessonByUniqueId(@PathVariable("lessonUniqueId") String lessonUniqueId, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			Lesson lesson = lessonService.getLessonsByUniqueId(lessonUniqueId);
			responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, AppConstants.BLANK,
					lesson, null);
			if(Objects.isNull(lesson)) {
				responseVO = new ResponseVO(HttpServletResponse.SC_NOT_FOUND, AppConstants.TEXT_ERROR, MessageReader.READER.getProperty("lesson.data.invalid.id"),
						lesson, null);
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
		response.setStatus(responseVO.getStatus());
		return responseVO;
	}

	// this method only for tutor profile to list all available lesson
	@RequestMapping(value = "/available", method = RequestMethod.GET)
	public ResponseVO getAvailableLessons(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.SUPER_ADMIN |
					RoleStates.ADMIN | RoleStates.TUTOR)) {
				List<LessonDto> lessons = lessonService.getAvailableLessons(LessonStates.AVAILABLE);
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

	/**
	 * This method used to fetch all the lesson by status.
	 * Can be used by all profile role.
	 *
	 */
	@RequestMapping(value = "/status" +"/{lessonStatus}", method = RequestMethod.GET)
	public ResponseVO getLessonByStatus(@PathVariable long lessonStatus, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			// if profile is student than use student profile id in created profile id for lesson
			if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.STUDENT | RoleStates.TUTOR)) {
				responseVO = lessonService.getLessonByProfileAndStatus(currentProfile, lessonStatus);
			}
			else if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.SUPER_ADMIN |
					RoleStates.ADMIN + RoleStates.SUPPORT)) {
				responseVO = lessonService.getLessonByStatusList(lessonStatus);
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	// TODO: might be we may need to use lessonAuthorize annotations
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
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public ResponseVO saveFeedback(@RequestBody Lesson lesson,
										 HttpServletRequest request, HttpServletResponse response)  throws AppException {
		ResponseVO responseVO = null;
		try {
			Profile currentProfile = AppUtils.getCurrentUserProfile(request);
			// check if user is allowed to take the action
			if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.STUDENT)) {
				responseVO = lessonService.saveFeedback(lesson);
			}
			else {
				responseVO = new ResponseVO(HttpServletResponse.SC_FORBIDDEN, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.message.lesson.notallowed.action"));
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
		} catch (AppException e) {
			logger.error("An exception occurred while adding info to audit table :", e);
		}
	}
}
