package com.tutoring.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.model.UserSupport;
import com.tutoring.service.SupportService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.MailService;
import com.tutoring.util.Mappings;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;

/**
 * Created by chirag.agarwal on 01-10-2017.
 */

@RestController
@RequestMapping(Mappings.SUPPORT)
public class SupportController {

	@Autowired
	private SupportService supportService;

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseVO saveQuery(@RequestBody UserSupport support, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(request);
			if(Objects.nonNull(profile)) {
				support.setCreatedBy(profile.getUsername());
				support.setUserId(profile.getId());
				support = supportService.save(support);
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.support.post.query"),
						support, null);
				mailService.sentEmail(support.getEmail(), null, null, "REQ" + String.format("%03d", support.getId()) + ": "+ support.getTitle(), support.getMessage(), null);
			} else {
				responseVO = new ResponseVO(HttpServletResponse.SC_UNAUTHORIZED, AppConstants.TEXT_ERROR, MessageReader.READER.getProperty("api.message.lesson.notallowed.action"));
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}
	
}
