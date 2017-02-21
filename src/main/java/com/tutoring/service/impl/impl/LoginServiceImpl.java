package com.tutoring.service.impl.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutoring.dao.ProfileDAO;
import com.tutoring.model.Profile;
import com.tutoring.service.impl.LoginService;
import com.tutoring.util.ApplicationConstants;
import com.tutoring.util.JWTGenerator;
import com.tutoring.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public class LoginServiceImpl implements LoginService {

    @Autowired
    ProfileDAO profileDAO;
    @Autowired
    JWTGenerator jwtGenerator;

    @Override
    public Map<String, String> validateUser(Profile profile) throws JsonProcessingException{
        Map<String,String> userMap = null;
        String accessToken;
        ObjectMapper mapper = new ObjectMapper();
        Profile returnProfile = profileDAO.findByEmail(profile.getEmail());
        if(Objects.nonNull(returnProfile)){
            boolean passwordVerify =  PasswordUtil.verifyPassword(profile.getPassword(), returnProfile.getPassword());
            if(passwordVerify){
                accessToken = jwtGenerator.encrypt(profile.getEmail());
                userMap = new HashMap<>();
                userMap.put(ApplicationConstants.PROFILE,mapper.writeValueAsString(returnProfile));
                userMap.put(ApplicationConstants.ACCESS_TOKEN, accessToken);
                return userMap;
            }
            return userMap;
        }
        return userMap;
    }
}
