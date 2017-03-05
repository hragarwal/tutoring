package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 04-03-2017.
 */
public interface CommonService {

	/**
	 * 
	 * @param emailId - email id of user profile
	 * @return response for forgot password
	 * @throws AppException
	 */
    public ResponseVO forgotPassword (String emailId) throws AppException;
    
    /**
     * Returns the user profile details.
     * @param passwordUpdateProfile - updated user profile details
     * @param userProfile - user profile details
     * @return user profile
     * @throws AppException
     */
    public ResponseVO changePassword (Profile passwordUpdateProfile, Profile userProfile) throws AppException;
}
