package com.tutoring.controller;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.CommonService;
import com.tutoring.service.LoginService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Objects;

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
			profile.setEmail(profile.getEmail().toLowerCase());
			responseVO = loginService.validateUser(profile);
			if(Objects.nonNull(responseVO) && responseVO.getStatus() == AppConstants.SUCCESS) {
				request.getSession().setAttribute(AppConstants.ACCESS_TOKEN, responseVO.getAccessToken());
				request.getSession().setAttribute(AppConstants.PROFILE, (Profile) responseVO.getData());
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;
	}

	@RequestMapping(value = "/files/delete", method = RequestMethod.DELETE)
	public void deleteTemporaryUserFiles(HttpServletRequest httpServletRequest) throws AppException{
		try{
			long profileId = AppUtils.getCurrentUserProfileID(httpServletRequest);
			File file = new File(profileDirectory + profileId);
			FileUtils.deleteDirectory(file);
		}catch (Exception e){
			throw new AppException(e);
		}
	}

	@RequestMapping(value = Mappings.FORGOT_PASSWORD, method = RequestMethod.POST)
	public ResponseVO forgotPassword(@RequestBody String emailId) throws AppException{
		ResponseVO responseVO;
		try {
			responseVO =  commonService.forgotPassword(emailId);
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
			if(responseVO.getStatus()==AppConstants.SUCCESS) {
				request.getSession().setAttribute(AppConstants.PROFILE, responseVO.getData());
			} else if(responseVO.getStatus() == 2) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}catch (Exception e){
			throw new AppException(e);
		}
		return responseVO;
	}

}
