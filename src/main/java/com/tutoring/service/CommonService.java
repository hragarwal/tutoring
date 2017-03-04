package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 04-03-2017.
 */
public interface CommonService {

    ResponseVO forgotPassword (String emailId) throws AppException;
}
