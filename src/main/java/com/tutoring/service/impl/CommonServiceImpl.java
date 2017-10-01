package com.tutoring.service.impl;

import java.text.MessageFormat;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.ProfileDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.CommonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.MailService;
import com.tutoring.util.MessageReader;
import com.tutoring.util.PasswordUtil;
import com.tutoring.util.RandomNumberGenerator;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 04-03-2017.
 */

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private MailService mailService;

	@Override
	public ResponseVO forgotPassword(String emailId) throws AppException { 
		Profile profile = profileDAO.findByEmail(emailId);
		ResponseVO responseVO= null;
		if(Objects.nonNull(profile)){
			String newTempPassword = RandomNumberGenerator.randomAlphaNumeric(6);
			profile.setPassword(PasswordUtil.hashPassword(newTempPassword));
			String formattedMessage = MessageFormat.format(MessageReader.READER.getProperty("api.message.forgot.password.success"), emailId);
			mailService.sentEmail(profile.getEmail(), null, null,
					MessageReader.READER.getProperty("api.email.forgotPassword.subject"), newTempPassword, null);
			responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE,
					formattedMessage);
		}else{
			responseVO = new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_ERROR,
					MessageReader.READER.getProperty("api.login.invalid.email"));
		}
		return responseVO;
	}

	@Override
	public ResponseVO changePassword(Profile passwordUpdateProfile, Profile userProfile) throws AppException {
		ResponseVO responseVO;
		userProfile = profileDAO.findOne(userProfile.getId());
		boolean passwordVerify =  PasswordUtil.verifyPassword(passwordUpdateProfile.getOldPassword(),
				userProfile.getPassword());
		if(passwordVerify) {
			Profile profile = profileDAO.findOne(userProfile.getId());
			if(Objects.nonNull(profile)) {
				profile.setPassword(PasswordUtil.hashPassword(passwordUpdateProfile.getConfirmPassword()));
				responseVO = new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE,
						MessageReader.READER.getProperty("api.message.message.password.success"), profile, null);
			} else {
				responseVO = new ResponseVO(HttpServletResponse.SC_UNAUTHORIZED, AppConstants.TEXT_ERROR,
						MessageReader.READER.getProperty("api.message.message.password.change.error"));
			}
		} else {
			responseVO = new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_ERROR,
				MessageReader.READER.getProperty("api.message.message.password.error"));
		}
		return responseVO;
	}
}
