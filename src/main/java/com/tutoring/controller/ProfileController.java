package com.tutoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tutoring.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

@RestController
public class ProfileController{
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value = Mappings.PROFILE, method = RequestMethod.POST)
	public ResponseVO createProfile(@RequestBody Profile profile, HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			responseVO = profileService.createProfile(profile);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.UPDATE_PROFILE, method = RequestMethod.PUT)
	public ResponseVO updateProfile(@RequestBody Profile profile, HttpServletRequest httpServletRequest,
									HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			responseVO = profileService.updateProfile(profile);
			if(responseVO.getStatus()==AppConstants.SUCCESS) {
				httpServletRequest.getSession().setAttribute(AppConstants.PROFILE, responseVO.getData());
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

}
