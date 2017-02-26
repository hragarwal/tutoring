package com.tutoring.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.exception.AppException;
import com.tutoring.service.FileService;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
                                 HttpServletResponse response) throws AppException{
        ResponseVO responseVO;
        try {
            responseVO = fileService.uploadFile(multipartHttpServletRequest);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new AppException(e);
        }
        return responseVO;

    }

}
