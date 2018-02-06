package com.tutoring.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.exception.AppException;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */
public interface FileService {

    ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
                          long profileId, String lessonUniqueId) throws IOException,AppException;
    byte[] downloadFile(String lessonUniqueId, String filename, String fileType) throws AppException;
}
