package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LoginService {
	
	 /**
	  * Authenticate the user on the basis of provided details. 
	  * @param profile - profile details
	  * @return - SUCCESS if profile details valid otherwise false
	  * @throws AppException - throws exception
	  */
     public ResponseVO validateUser(Profile profile) throws AppException;
}
