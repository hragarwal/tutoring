package com.tutoring.aspect;

import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppUtils;
import com.tutoring.util.LessonStates;
import com.tutoring.util.MessageReader;
import com.tutoring.util.RoleStates;

/**
 * This aspect authorize data if user is not authorize to view data throws exception.
 */
@Aspect
@Component
public class AuthorizeAspect {

	/** The lesson service. */
	@Autowired
	private LessonService lessonService;
	
	/**
	 * This advice used to authorize lesson details for only allowed user.
	 *
	 * @param joinPoint the join point
	 * @throws AppException the app exception
	 */
	@Before("@annotation(com.tutoring.annotations.LessonAuthorize) && args(..)")
	public void lessonAuthorizeAdvice(JoinPoint joinPoint) throws AppException {

		Profile currentProfile = null;
		HttpServletResponse response = null;
		String lessonUniqueId = null;
		Object [] args = joinPoint.getArgs();
		if(Objects.nonNull(args)) {
			for(Object object : args) {
				if(object instanceof String) {
					lessonUniqueId = (String) object;
				} else if(object instanceof ServletRequest) {
					HttpServletRequest request = (HttpServletRequest) object;
					currentProfile = AppUtils.getCurrentUserProfile(request);
				} else if(object instanceof ServletResponse) {
					response = (HttpServletResponse) object;
				}
			}
		}
		
		if(RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.SUPER_ADMIN | RoleStates.ADMIN )) {
			return;
		}

		else {
			//fetch the lesson details
			Lesson lesson = lessonService.getLessonsByUniqueId(lessonUniqueId);
			
			// allowed everyone if lesson states is available and profile is tutor 
			if(lesson == null || LessonStates.isLessonStates(lesson.getStatus().getId(), LessonStates.AVAILABLE) && 
					RoleStates.isRoleAccessible(currentProfile.getRole().getId(), RoleStates.TUTOR )) {
				return;	
			} 
			
			// for student role
			if(currentProfile.getRole().getId() == RoleStates.STUDENT ){
				isUserAuthorize(response, currentProfile.getId(), lesson.getStudentProfile().getId());
			}
			
			// for tutor role
			if(currentProfile.getRole().getId() == RoleStates.TUTOR) {
				isUserAuthorize(response, currentProfile.getId(), lesson.getTutorProfile().getId()); 
			}
		}
	}
	

	/**
	 * Checks if is user authorize.
	 *
	 * @param response the response
	 * @param currentUserID the current user ID
	 * @param allowedDataId the allowed data id
	 * @return true, if is user authorize
	 * @throws AppException the app exception
	 */
	private boolean isUserAuthorize(HttpServletResponse response, long currentUserID, long allowedDataId) throws AppException {
		if(currentUserID == allowedDataId)
			return true;
		
		if(Objects.nonNull(response)) 
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		throw new AppException(MessageReader.READER.getProperty("api.unauthorized.data.error"));
	}
	
}
