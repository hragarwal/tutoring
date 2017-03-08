package com.tutoring.service;

import java.io.IOException;

import com.tutoring.exception.AppException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */
public interface FileService {

    ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
                          long profileId, long lessonId) throws IOException,AppException;
    byte[] downloadFile(long lessonId, String filename, String fileType) throws AppException;
}
