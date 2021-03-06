package com.tutoring.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.CommonService;
import com.tutoring.service.LoginService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 23-02-2017.
 */
@RestController
public class CommonController{

	@Autowired
	private LoginService loginService;

	@Autowired
	private CommonService commonService;

	@Value("${file.save.location.profile}")
	private String profileDirectory;

	@RequestMapping(value = Mappings.LOGIN, method = RequestMethod.POST)
	public ResponseVO validateUser(@RequestBody Profile profile, HttpServletRequest request, HttpServletResponse response) throws AppException {
		ResponseVO responseVO;
		try {
			if(AppUtils.isValidEmailAddress(profile.getEmail()))
				profile.setEmail(profile.getEmail().toLowerCase());
			responseVO = loginService.validateUser(profile);
			if(Objects.nonNull(responseVO) && responseVO.getStatus() == HttpServletResponse.SC_OK) {
				request.getSession().setAttribute(AppConstants.ACCESS_TOKEN, responseVO.getAccessToken());
				request.getSession().setAttribute(AppConstants.PROFILE, (Profile) responseVO.getData());
			}
			response.setStatus(responseVO.getStatus());
		} catch (Exception e) {
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = "/files/delete", method = RequestMethod.DELETE)
	public void deleteTemporaryUserFiles(HttpServletRequest httpServletRequest) throws AppException{
		try{
			Profile profile = AppUtils.getCurrentUserProfile(httpServletRequest);
			AppUtils.deleteDirectoryForUser(profile,profileDirectory);
		}catch (Exception e){
			throw new AppException(e);
		}
	}

	@RequestMapping(value = Mappings.FORGOT_PASSWORD, method = RequestMethod.POST)
	public ResponseVO forgotPassword(@RequestBody String emailId, HttpServletRequest request,
			HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			responseVO =  commonService.forgotPassword(emailId);
			response.setStatus(responseVO.getStatus());
		}catch (Exception e){
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.CHANGE_PASSWORD, method = RequestMethod.POST)
	public ResponseVO changePassword(@RequestBody Profile passwordUpdateProfile,  HttpServletRequest request,
			HttpServletResponse response) throws AppException{
		ResponseVO responseVO;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(request);
			responseVO = commonService.changePassword(passwordUpdateProfile, profile);
			if(responseVO.getStatus()== HttpServletResponse.SC_OK) {
				request.getSession().setAttribute(AppConstants.PROFILE, responseVO.getData());
			} 
			response.setStatus(responseVO.getStatus());
		}catch (Exception e){
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = Mappings.LOGOUT, method = RequestMethod.GET)
	public void logout(HttpServletRequest request){
		request.getSession().removeAttribute(AppConstants.PROFILE);
		request.getSession().removeAttribute(AppConstants.ACCESS_TOKEN);
	}

}
