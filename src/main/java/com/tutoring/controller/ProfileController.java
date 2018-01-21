package com.tutoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tutoring.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

import java.util.Objects;

@RestController
public class ProfileController{
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value = Mappings.SIGN_UP, method = RequestMethod.POST)
	public ResponseVO createProfile(@RequestBody Profile profile, HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			profile.setEmail(profile.getEmail().toLowerCase());
			responseVO = profileService.createProfile(profile);
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.PROFILE, method = RequestMethod.PUT)
	public ResponseVO updateProfile(@RequestBody Profile profile, HttpServletRequest httpServletRequest,
									HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			responseVO = profileService.updateProfile(profile);
			if(responseVO.getStatus()==AppConstants.SUCCESS) {
				httpServletRequest.getSession().setAttribute(AppConstants.PROFILE, responseVO.getData());
			} else if(responseVO.getStatus() == 2) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.PROFILE, method = RequestMethod.GET)
	public ResponseVO getCurrentProfile(HttpServletRequest httpServletRequest,
									HttpServletResponse response) throws AppException{
		ResponseVO responseVO = null;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(httpServletRequest);
			if(Objects.nonNull(profile)) {
				profile = profileService.getProfile(profile.getId());
				if(profile != null) {
					responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE,
							"Profile details for id " + profile.getId() + " retrieved successfully.",
							profile, null);
				}
			} else  {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

}
