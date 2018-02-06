package com.tutoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.Objects;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Value("${file.save.location.profile}")
	private String profileDirectory;


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
			HttpServletResponse response,
			@RequestParam(name = "lessonUniqueId", required = false) String lessonUniqueId) throws AppException{
		ResponseVO responseVO;
		try {
			Profile profile = AppUtils.getCurrentUserProfile(multipartHttpServletRequest);
			responseVO = fileService.uploadFile(multipartHttpServletRequest, profile.getId(), lessonUniqueId);
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
		return responseVO;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void deleteFile(HttpServletRequest httpServletRequest,
								 HttpServletResponse response,
								 @RequestParam(name = "fileName", required = false) String fileName) throws AppException {
		try {
			Profile profile = AppUtils.getCurrentUserProfile(httpServletRequest);
			if(Objects.nonNull(fileName)) {
				AppUtils.deleteFileFromUserDirectory(profile, profileDirectory, fileName);
			} else {
				AppUtils.deleteDirectoryForUser(profile, profileDirectory);
			}
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new AppException(e);
		}
	}

	@RequestMapping(value = "/download/{lessonUniqueId}", method = RequestMethod.GET)
	public void downloadFile (@PathVariable("lessonUniqueId") String lessonUniqueId,
			@RequestParam("filename") String filename,
			@RequestParam("fileType") String fileType,
			HttpServletResponse response) throws AppException{
		try{
			byte [] bytes = fileService.downloadFile(lessonUniqueId,filename,fileType);
			response.addHeader("Content-Disposition", "attachment; filename=\"" +
					AppUtils.getActualFilenameFromServerFile(filename) + "\"");
			AppUtils.prepareFileAndFlushResponse(response, bytes,filename);
		}catch (Exception e){
			throw new AppException(e);
		}
	}

}
