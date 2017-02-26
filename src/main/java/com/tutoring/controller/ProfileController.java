package com.tutoring.controller;

import javax.servlet.http.HttpServletResponse;

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

}
