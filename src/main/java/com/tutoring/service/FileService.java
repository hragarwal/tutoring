package com.tutoring.service;

import com.tutoring.util.ResponseVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */
public interface FileService {

    ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException;
}
