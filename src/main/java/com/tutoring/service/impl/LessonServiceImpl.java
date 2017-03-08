package com.tutoring.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.LessonDAO;
import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.dao.SubjectDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Files;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.LessonStates;
import com.tutoring.util.MessageReader;
import com.tutoring.util.ResponseVO;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonDAO lessonDAO;

	@Autowired
	private LessonStatusDAO lessonStatusDAO;

	@Autowired
	private SubjectDAO subjectDAO;

	@Value("${file.save.location.profile}")
	private String profileSaveLocation;
	@Value("${file.save.location.lesson}")
	private String lessonSaveLocation;

	public boolean createLesson(Lesson lesson,  Profile profile) throws AppException {
		try {
			lesson.setSubject(subjectDAO.findOne(Long.valueOf(lesson.getSubjectID())));
			lesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.AVAILABLE)));
			Lesson returnLesson = lessonDAO.save(lesson);
			File profileDir = new File(profileSaveLocation + profile.getId());
			File lessonDir = new File(lessonSaveLocation + returnLesson.getId() + AppConstants.QUESTION_DIR);
			FileUtils.copyDirectory(profileDir, lessonDir);
			Set<Files> questionFileList = new HashSet<>();
			Files questionFile;
			File[] listOfFiles = lessonDir.listFiles();
			for(File file : listOfFiles){
				if(file.isFile()){
					questionFile = new Files();
					questionFile.setFileType(AppConstants.FILE_QUESTION_TYPE);
					questionFile.setFilePath(file.getName());
					questionFile.setActualFileName(AppUtils.getActualFilenameFromServerFile(file.getName()));
					questionFile.setCreatedBy(profile.getEmail());
					questionFileList.add(questionFile);
				}
			}
			returnLesson.setFileList(questionFileList);
			return true;
		}catch (IOException e){
			throw new AppException(e);
		}
	}

	@Override
	public List<Lesson> getAllLessons() throws AppException {
		Iterator<Lesson> lessons =  lessonDAO.findAll().iterator();
		return IteratorUtils.toList(lessons);
	}

	@Override
	public List<Lesson> getLessonsByProfile(long profileId) throws AppException {
		return lessonDAO.getLessonsByProfileID(profileId);
	}

	@Override
	public Lesson getLessonsByLessonId(long lessonId) throws AppException {
		return lessonDAO.findOne(Long.valueOf(lessonId));
	}

	@Override
	public List<Lesson> getAvailableLessons(long lessonStatus) throws AppException {
		return lessonDAO.getAvailableLessons(lessonStatus);
	}

	@Override
	public ResponseVO updateLessonStatus(Lesson lesson, Profile currentProfile) throws IOException {
		Lesson returnLesson = lessonDAO.findOne(lesson.getId());
		String formattedMessage;
		if(returnLesson.getStatus().getId() == lesson.getStatus().getId()) {
			formattedMessage = MessageFormat.format(MessageReader.READER.getProperty("api.lessonstatus.update.error"), LessonStates.getAllLessonStates().get(lesson.getStatus().getId()));
			return new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE, formattedMessage);
		}
		
		// if lesson update is for ACCEPTED
		if(lesson.getStatus().getId() == LessonStates.ACCEPTED) {
			// if due amount not specified 
			if(lesson.getDueAmount() <=0) {
				return new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_MESSAGE, MessageReader.READER.getProperty("api.insufficient.data.error"));
			}
			returnLesson.setDueAmount(lesson.getDueAmount());
			returnLesson.setEstimatedWorkEffort(lesson.getEstimatedWorkEffort());
			returnLesson.setModifiedBy(currentProfile.getEmail());
			returnLesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.ACCEPTED)));
			returnLesson.setTutorProfile(currentProfile);
		}
		/* started working IN PROGRESS */
		else if(lesson.getStatus().getId() == LessonStates.IN_PROGRESS) {
			returnLesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.IN_PROGRESS)));
			returnLesson.setModifiedBy(currentProfile.getEmail());
		}
		/* Waiting Payment task is finished by tutor*/
		else if(lesson.getStatus().getId() == LessonStates.WAITING_PAYMENT) {
			returnLesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.WAITING_PAYMENT)));
			returnLesson.setModifiedBy(currentProfile.getEmail());
		}
		/* Answer submitted by tutor*/
		else if(lesson.getStatus().getId() == LessonStates.SUBMITTED){
			if(StringUtils.isEmpty(lesson.getLessonAnswerDesc())){
				return new ResponseVO(AppConstants.ERROR,AppConstants.TEXT_ERROR,
						MessageReader.READER.getProperty("api.message.lesson.answer.description"));
			}
			returnLesson.setLessonAnswerDesc(lesson.getLessonAnswerDesc());
			Set<Files> fileList = returnLesson.getFileList();
			File profileDir = new File(profileSaveLocation + currentProfile.getId());
			File lessonDir = new File(lessonSaveLocation + returnLesson.getId() + AppConstants.ANSWER_DIR);
			FileUtils.copyDirectory(profileDir, lessonDir);
			Files answerFile;
			File[] listOfFiles = lessonDir.listFiles();
			for(File file : listOfFiles){
				if(file.isFile()){
					answerFile = new Files();
					answerFile.setFileType(AppConstants.FILE_ANSWER_TYPE);
					answerFile.setFilePath(file.getName());
					answerFile.setActualFileName(AppUtils.getActualFilenameFromServerFile(file.getName()));
					answerFile.setCreatedBy(currentProfile.getEmail());
					fileList.add(answerFile);
				}
			}
			returnLesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.SUBMITTED)));
			returnLesson.setModifiedBy(currentProfile.getEmail());
		}
		//Student marked lesson as completed
		else if(lesson.getStatus().getId() == LessonStates.COMPLETED){
			returnLesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.COMPLETED)));
			returnLesson.setModifiedBy(currentProfile.getEmail());
		}

		//Clean the user profile directory for after every status update because user might upload it by changing just
		//submitted status on UI and than again reset to some lower value
		AppUtils.deleteDirectoryForUser(currentProfile,profileSaveLocation);
		formattedMessage = MessageFormat.format(MessageReader.READER.getProperty("api.lessonstatus.update.success"), LessonStates._ACCEPTED);
		return new ResponseVO(AppConstants.SUCCESS, AppConstants.TEXT_MESSAGE, formattedMessage);
	}
	
	
}
