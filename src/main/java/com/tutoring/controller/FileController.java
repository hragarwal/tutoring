package com.tutoring.controller;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.FileService;
import com.tutoring.util.AppUtils;
import com.tutoring.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

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
            Profile profile = AppUtils.getCurrentUserProfile(multipartHttpServletRequest);
            responseVO = fileService.uploadFile(multipartHttpServletRequest, profile.getId());
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new AppException(e);
        }
        return responseVO;

    }

    @RequestMapping(value = "/download/{lessonId}", method = RequestMethod.GET)
    public void downloadFile (@PathVariable("lessonId") long lessonId,
                              @RequestParam("filename") String filename, HttpServletResponse response) throws AppException{
        try{
            byte [] bytes = fileService.downloadFile(lessonId,filename);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            AppUtils.prepareFileAndFlushResponse(response, bytes,filename);
        }catch (Exception e){
            throw new AppException(e);
        }
    }

}
