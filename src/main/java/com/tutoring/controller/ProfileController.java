package com.tutoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

@RestController
public class ProfileController extends AppController {
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value = Mappings.PROFILE, method = RequestMethod.POST)
	public ResponseVO createProfile(@RequestBody Profile profile) {
		ResponseVO responseVO = null;
		try {
			responseVO = profileService.createProfile(profile);
		} catch (Exception e) {
			//throw new AppException(logger, msgKey, entityName)
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			e.getMessage();
		}
		return responseVO;
	}

}
