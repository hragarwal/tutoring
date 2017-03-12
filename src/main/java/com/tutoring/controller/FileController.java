package com.tutoring.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.exception.AppException;
import com.tutoring.model.Profile;
import com.tutoring.service.FileService;
import com.tutoring.util.AppUtils;
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
			HttpServletResponse response,
			@RequestParam(name = "lessonId", required = false) long lessonId) throws AppException{
		ResponseVO responseVO;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(multipartHttpServletRequest);
			responseVO = fileService.uploadFile(multipartHttpServletRequest, profile.getId(), lessonId);
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;

	}

	@RequestMapping(value = "/download/{lessonId}", method = RequestMethod.GET)
	public void downloadFile (@PathVariable("lessonId") long lessonId,
			@RequestParam("filename") String filename,
			@RequestParam("fileType") String fileType,
			HttpServletResponse response) throws AppException{
		try{
			byte [] bytes = fileService.downloadFile(lessonId,filename,fileType);
			response.addHeader("Content-Disposition", "attachment; filename=\"" +
					AppUtils.getActualFilenameFromServerFile(filename) + "\"");
			AppUtils.prepareFileAndFlushResponse(response, bytes,filename);
		}catch (Exception e){
			throw new AppException(e);
		}
	}

}
