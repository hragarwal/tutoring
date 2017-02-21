package com.tutoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.model.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface ProfileService {

    Map<String,String> createProfile(Profile profile) throws JsonProcessingException;
    Profile updateProfile(Profile profile);
    Profile getProfile(long id);
    List<Profile> getAllProfiles();
}
