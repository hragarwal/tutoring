package com.tutoring.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
    public ResponseVO forgotPassword(String emailId) throws AppException{
        Profile profile = profileDAO.findByEmail(emailId);
        ResponseVO responseVO=null;
        if(Objects.nonNull(profile)){
            String newTempPassword = RandomNumberGenerator.randomAlphaNumeric(6);
            profile.setPassword(PasswordUtil.hashPassword(newTempPassword));
            mailService.sendMail(newTempPassword,
                    MessageReader.READER.getProperty("api.email.forgotPassword.subject"),emailId);
            responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE,
                    MessageReader.READER.getProperty("api.message.message.email.success"));
        }else{
            responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR,
                    MessageReader.READER.getProperty("api.login.invalid.email"));
        }
        return responseVO;
    }

    @Override
    public ResponseVO changePassword(Profile passwordUpdateProfile, Profile userProfile) throws AppException {
        ResponseVO responseVO;
        boolean passwordVerify =  PasswordUtil.verifyPassword(passwordUpdateProfile.getOldPassword(),
                userProfile.getPassword());
        if(passwordVerify){
            Profile profile = profileDAO.findOne(userProfile.getId());
            profile.setPassword(PasswordUtil.hashPassword(passwordUpdateProfile.getConfirmPassword()));
            responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE,
                    MessageReader.READER.getProperty("api.message.message.password.success"));
            responseVO.setData(profile);
        }else{
            responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR,
                    MessageReader.READER.getProperty("api.message.message.password.error"));
        }
        return responseVO;
    }
}
