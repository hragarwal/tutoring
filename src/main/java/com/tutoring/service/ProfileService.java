package com.tutoring.service;

import java.util.List;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface ProfileService {

    /**
     * Creates the user profile.
     *
     * @param profile the profile
     * @return the response VO
     * @throws AppException the app exception
     */
    public ResponseVO createProfile(Profile profile) throws AppException;

    /**
     *
     * @param profile
     * @return
     * @throws AppException
     */
    public ResponseVO updateProfile(Profile profile) throws AppException;
    
    /**
     * Gets the user profile by user id.
     *
     * @param id the id
     * @return the profile
     * @throws AppException the app exception
     */
    public Profile getProfile(long id) throws AppException;
    
    /**
     * Gets the all user profiles.
     *
     * @return the all profiles
     * @throws AppException the app exception
     */
    public List<Profile> getAllProfiles() throws AppException;
}
