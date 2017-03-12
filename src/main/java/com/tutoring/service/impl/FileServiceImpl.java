package com.tutoring.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import com.tutoring.controller.ChatController;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Message;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.service.MessageService;
import com.tutoring.service.ProfileService;
import com.tutoring.util.AppUtils;
import com.tutoring.util.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LessonService lessonService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MessageService messageService;
    
    @Autowired
    ChatController chatController;

    @Override
    public ResponseVO uploadFile(MultipartHttpServletRequest multipartHttpServletRequest,
                                 long profileId, long lessonId) throws IOException, AppException{
        ResponseVO responseVO = new ResponseVO();
        FileOutputStream fileOutputStream=null;
        File file;
        try {
            Iterator<String> itr = multipartHttpServletRequest.getFileNames();
            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(uploadedFile);
                String filename = multipartFile.getOriginalFilename();
                String serverFileName = AppUtils.getUniqueFilename(filename);
                byte[] bytes = multipartFile.getBytes();
                if(filename.contains(AppConstants.FILE_TYPE_EXE)){
                    return new ResponseVO(AppConstants.ERROR,AppConstants.TEXT_ERROR,
                            MessageReader.READER.getProperty("api.message.file.upload.exe"));
                }else if(bytes.length<=0){
                    return new ResponseVO(AppConstants.ERROR,AppConstants.TEXT_ERROR,
                            MessageReader.READER.getProperty("api.message.file.upload.noData"));
                }else if(lessonId>0){
                    file = new File(lessonDirectory + lessonId + AppConstants.QUESTION_DIR +
                            serverFileName);
                }else {
                    file = new File(profileDirectory + profileId + AppConstants.FORWARD_SLASH +
                            serverFileName);
                }
                file.getParentFile().mkdirs();
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                if(lessonId>0){
                    Message message = new Message();
                    Lesson lesson = lessonService.getLessonsByLessonId(lessonId);
                    Profile currentProfile = profileService.getProfile(profileId);
                    message.setSenderProfile(currentProfile);
                    if(currentProfile.getId() == lesson.getStudentProfile().getId()) {
                        Profile tutorProfile = lesson.getTutorProfile();
                        if(Objects.nonNull(tutorProfile))
                            message.setReceiverProfile(tutorProfile);
                    }else{
                        message.setReceiverProfile(lesson.getStudentProfile());
                    }
                    message.setActualFileName(filename);
                    message.setLesson(lesson);
                    message.setDescription(serverFileName);
                    message.setMessageType(AppConstants.MESSAGE_TYPE_FILE);
                    message.setCreatedBy(currentProfile.getEmail());
                    responseVO = messageService.save(message);
                    // send file message for lesson
                    chatController.sendFileMessage(message);
                }
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
    public byte[] downloadFile(long lessonId, String filename, String fileType) throws AppException{
        byte[] bytes;
        try {
            String subDirectory = AppConstants.QUESTION_DIR;
            if(fileType.equalsIgnoreCase(AppConstants.FILE_ANSWER_TYPE)){
                subDirectory = AppConstants.ANSWER_DIR;
            }
            StringBuilder fileLocation = new StringBuilder(lessonDirectory +
                    lessonId + subDirectory + filename);
            bytes = AppUtils.getFileFromLocalFile(fileLocation.toString());
        }catch (Exception e){
            throw new AppException(e);
        }
        return bytes;
    }
}
