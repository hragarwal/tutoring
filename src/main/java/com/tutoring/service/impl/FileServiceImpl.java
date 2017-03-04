package com.tutoring.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import com.tutoring.exception.AppException;
import com.tutoring.util.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tutoring.service.FileService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Value("${file.save.location.profile}")
    private String profileDirectory;

    @Value("${file.save.location.lesson}")
    private String lessonDirectory;

    @Override
    public ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
                                 long profileId) throws IOException{
        ResponseVO responseVO = new ResponseVO();
        FileOutputStream fileOutputStream=null;
        try {
            Iterator<String> itr = multipartHttpServletRequest.getFileNames();
            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(uploadedFile);
                String filename = multipartFile.getOriginalFilename();
                byte[] bytes = multipartFile.getBytes();
                File file = new File(profileDirectory+ profileId+AppConstants.FORWARD_SLASH+ filename);
                file.getParentFile().mkdirs();
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                responseVO.setStatus(AppConstants.SUCCESS);
            }
        }finally {
            if(Objects.nonNull(fileOutputStream)){
                fileOutputStream.close();
            }
        }
        return responseVO;
    }

    @Override
    public byte[] downloadFile(long lessonId, String filename) throws AppException{
        byte[] bytes;
        try {
            StringBuilder fileLocation = new StringBuilder(lessonDirectory + lessonId + AppConstants.FORWARD_SLASH + filename);
            bytes = AppUtils.getFileFromLocalFile(fileLocation.toString());
        }catch (Exception e){
            throw new AppException(e);
        }
        return bytes;
    }
}
