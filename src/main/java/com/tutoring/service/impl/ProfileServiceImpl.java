package com.tutoring.service.impl;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import com.tutoring.model.dto.ProfileDto;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.ProfileDAO;
import com.tutoring.dao.RoleDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.JWTGenerators;
import com.tutoring.util.MessageReader;
import com.tutoring.util.PasswordUtil;
import com.tutoring.util.ResponseVO;
import com.tutoring.util.RoleStates;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDAO profileDAO;
    
    @Autowired
    private JWTGenerators jwtGenerators;
    
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public ResponseVO createProfile(Profile profile) throws AppException {
    	String formattedMessage = null;
    	// check weather username is already exist.
    	if(Objects.nonNull(profileDAO.findByUsername(profile.getUsername()))) {
    		formattedMessage = MessageFormat.format(MessageReader.READER.getProperty("api.profile.create.usernameexist"), profile.getUsername());
    		return new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_MESSAGE, 
    				formattedMessage);
    	}
    	
    	// check weather email is already exist.
    	if(Objects.nonNull(profileDAO.findByEmail(profile.getEmail()))) {
    		formattedMessage = MessageFormat.format(MessageReader.READER.getProperty("api.profile.create.emailexist"), profile.getEmail());
    		return new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_MESSAGE, 
    				formattedMessage);
    	}
    	// try to create new profile
    	profile.setCreatedBy(profile.getUsername());
    	profile.setIsActive(true);
    	profile.setRole(roleDAO.findByName(RoleStates._STUDENT));
    	if(!AppUtils.valiateProfile(profile)) {
    		return  new ResponseVO(HttpServletResponse.SC_BAD_REQUEST, AppConstants.TEXT_MESSAGE, 
            		MessageReader.READER.getProperty("api.insufficient.data.error"));
    	}
    	profile.setPassword(PasswordUtil.hashPassword(profile.getPassword()));
        profile = profileDAO.save(profile);
        return new ResponseVO(HttpServletResponse.SC_OK, AppConstants.TEXT_MESSAGE,
        		MessageReader.READER.getProperty("api.profile.create.success"), new ProfileDto(profile), null);
    }

    @Override
    public ResponseVO updateProfile(Profile profile) {
        ResponseVO responseVO;
        Profile returnProfile = profileDAO.findOne(profile.getId());

        if(!profile.getEmail().equals(returnProfile.getEmail()) &&
                Objects.nonNull(profileDAO.findByEmail(profile.getEmail()))) {
            responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE,
                    MessageReader.READER.getProperty("api.profile.create.emailexist"));
        }else{
            returnProfile.setName(profile.getName());
            returnProfile.setEmail(profile.getEmail());
            returnProfile.setCountry(profile.getCountry());
            returnProfile.setContactNumber(profile.getContactNumber());
            returnProfile.setSkypeId(profile.getSkypeId());
            responseVO = new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE,
                    MessageReader.READER.getProperty("api.profile.update.success"));
            responseVO.setData(returnProfile);
        }
        return responseVO;
    }

    @Override
    public Profile getProfile(long id) {
        return profileDAO.findOne(id);
    }

    @Override
    public List<Profile> getAllProfiles() {
        Iterator<Profile> profileIterable =  profileDAO.findAll().iterator();
        return IteratorUtils.toList(profileIterable);
    }

}
