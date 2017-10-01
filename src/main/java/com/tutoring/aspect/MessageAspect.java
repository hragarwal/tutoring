package com.tutoring.aspect;

import java.util.List;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutoring.model.Message;
import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;

/**
 * This class process the message content before exchanging between users.
 * Use @annotation InvalidMessage with the method.
 * @author CHIRAG
 */
@Component
@Aspect
public class MessageAspect {

	@Autowired
	private ProfileService profileService;
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(MessageAspect.class);
	
	/**
	 * This advice process the message transfer between users. If any message contains illegal text
	 * like any email address or unwanted keyword or illegal web site URL. It stop processing those message
	 * and returns an error message to user.
	 *
	 * @param proceedingJoinPoint the proceeding join point
	 * @return the response VO
	 */
	@Around("@annotation(com.tutoring.annotations.InvalidMessage) && args(..)")
	public ResponseVO checkMessageAdvice(ProceedingJoinPoint  proceedingJoinPoint) {
		ResponseVO responseVO = null;
		try {
			Object [] args = proceedingJoinPoint.getArgs();
			Profile currentProfile = null;
			Message message = null;
			// retrieve the input arguments
			if(Objects.nonNull(args)) {
				for(Object object : args) {
					if(object instanceof ServletRequest) {
						HttpServletRequest request = (HttpServletRequest) object;
						currentProfile = AppUtils.getCurrentUserProfile(request);
					}
					else if(object instanceof Message) {
						message = (Message) object;
						currentProfile = profileService.getProfile(message.getCurrentProfile());
					} 
				}
			}
			
			if(Objects.isNull(message)) {
				proceedingJoinPoint.proceed();
			}
			else if(Objects.isNull(currentProfile)) {
				logger.info(AppConstants.NEW_LINE+ "Please check method signature -> profile == null: "+ 
						proceedingJoinPoint.toString() + AppConstants.NEW_LINE);
				responseVO = (ResponseVO) proceedingJoinPoint.proceed();
			}
			// any message is allowed 
			else if(currentProfile.isAllowedShare()) {
				responseVO = (ResponseVO) proceedingJoinPoint.proceed();
			}  
			else {
				List<String> emails = AppUtils.containsEmailInMessage(message.getDescription());
				// if trying to share personal information
				if(Objects.nonNull(emails) && !emails.isEmpty()) {
					logger.info("======== Personal Information Shared Capture ========");
					logger.info("Profile details= Username: " + currentProfile.getUsername()+ AppConstants.SPACE +" Role: " + currentProfile.getRole().getName());
					logger.info("Message details= Description: " + message.getDescription()); 
					logger.info("Sent By: " + currentProfile.getId()); 
					logger.info(" Lesson Id: " + message.getLesson().getId());
					logger.info("Filtered Emails: "+ emails);
					return new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, 
								MessageReader.READER.getProperty("api.message.contains.illegal"));
				} else {
					responseVO = (ResponseVO) proceedingJoinPoint.proceed();
				}
			}
		} catch (Throwable th) {
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			logger.error("An exception occured while executing advice checkMessageAdvice()", th);
		}
		return responseVO;
	}
}
