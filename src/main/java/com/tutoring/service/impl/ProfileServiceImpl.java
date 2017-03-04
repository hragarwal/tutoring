package com.tutoring.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
    	// check weather email is already exist.
    	if(Objects.nonNull(profileDAO.findByEmail(profile.getEmail()))) {
    		return new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE, 
    				MessageReader.READER.getProperty("api.profile.create.emailexist"));
    	}
    	// try to create new profile
    	profile.setRole(roleDAO.findByName(RoleStates._STUDENT));
    	profile.setPassword(PasswordUtil.hashPassword(profile.getPassword()));
        profile = profileDAO.save(profile);
        String accessToken = jwtGenerators.encrypt(profile.getEmail());
        return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, 
        		MessageReader.READER.getProperty("api.profile.create.success"), profile, accessToken);
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
                    MessageReader.READER.getProperty("api.profile.create.success"));
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
