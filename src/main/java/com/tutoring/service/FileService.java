package com.tutoring.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */
public interface FileService {

    ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest, long profileId) throws IOException;
}
