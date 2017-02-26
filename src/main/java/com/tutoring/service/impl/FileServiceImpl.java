package com.tutoring.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

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

    @Value("${file.save.location}")
    private String directory;

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
                File file = new File(directory+profileId+AppConstants.FORWARD_SLASH+filename);
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
}
