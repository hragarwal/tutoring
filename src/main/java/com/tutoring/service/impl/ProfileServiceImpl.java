package com.tutoring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutoring.dao.ProfileDAO;
import com.tutoring.model.Profile;
import com.tutoring.service.ProfileService;
import com.tutoring.util.ApplicationConstants;
import com.tutoring.util.JWTGenerators;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileDAO profileDAO;
    @Autowired
    JWTGenerators jwtGenerators;

    @Override
    public Map<String, String> createProfile(Profile profile) throws JsonProcessingException{
        Map<String,String> userMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Profile returnProfile = profileDAO.save(profile);
        String accessToken = jwtGenerators.encrypt(returnProfile.getEmail());
        userMap.put(ApplicationConstants.PROFILE,objectMapper.writeValueAsString(returnProfile));
        userMap.put(ApplicationConstants.ACCESS_TOKEN,accessToken);
        return userMap;
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileDAO.save(profile);
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
