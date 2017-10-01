package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.UserSupport;

public interface SupportService {

	public UserSupport save(UserSupport support) throws AppException;
	
}
