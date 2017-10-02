package com.tutoring.service.impl;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.ProfileDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.LoginService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.JWTGenerators;
import com.tutoring.util.MessageReader;
import com.tutoring.util.PasswordUtil;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private ProfileDAO profileDAO;
	
	@Autowired
	private JWTGenerators jwtGenerator;

	@Override
	public ResponseVO validateUser(Profile profile) throws AppException {
		ResponseVO responseVO;
		String accessToken;
		Profile returnProfile = profileDAO.authenticateUser(profile.getEmail());
		if(Objects.nonNull(returnProfile)){
			boolean passwordVerify =  PasswordUtil.verifyPassword(profile.getPassword(), returnProfile.getPassword());
			if(passwordVerify){
				accessToken = jwtGenerator.encrypt(profile.getEmail());
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE, "Login Successfull", returnProfile, accessToken);
			} else {
				responseVO = new ResponseVO(HttpServletResponse.SC_NOT_ACCEPTABLE, AppConstants.TEXT_ERROR, 
						MessageReader.READER.getProperty("api.login.invalid.password"));
			}
		} else {
			responseVO = new ResponseVO(HttpServletResponse.SC_NOT_ACCEPTABLE, AppConstants.TEXT_ERROR, 
					MessageReader.READER.getProperty("api.login.invalid.email"));
		}
		return responseVO;
	}
}
