package com.tutoring.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.model.Profile;
import com.tutoring.service.LoginService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 23-02-2017.
 */
@RestController
public class CommonController extends AppController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = Mappings.LOGIN, method = RequestMethod.POST)
	public ResponseVO validateUser(@RequestBody Profile profile, HttpServletRequest request, HttpServletResponse response) {
		ResponseVO responseVO = null;
		try {
			responseVO = loginService.validateUser(profile);
			if(Objects.nonNull(responseVO) && responseVO.getStatus() == AppConstants.SUCCESS) {
				request.getSession().setAttribute(AppConstants.ACCESS_TOKEN, responseVO.getAccessToken());
				request.getSession().setAttribute(AppConstants.PROFILE, (Profile) responseVO.getData());
			}
		} catch (Exception e) {
			//throw new AppException(logger, msgKey, entityName)
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			e.getMessage();
		}
		return responseVO;
	}

}
