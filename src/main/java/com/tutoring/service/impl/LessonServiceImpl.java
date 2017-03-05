package com.tutoring.service.impl;

import com.tutoring.dao.LessonDAO;
import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.dao.SubjectDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Files;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.service.LessonService;
import com.tutoring.util.LessonStates;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
			File lessonDir = new File(lessonSaveLocation + returnLesson.getId());
			FileUtils.copyDirectory(profileDir, lessonDir);
			Set<Files> questionFileList = new HashSet<>();
			Files questionFile;
			File[] listOfFiles = lessonDir.listFiles();
			for(File file : listOfFiles){
				if(file.isFile()){
					questionFile = new Files();
					questionFile.setFilePath(file.getName());
					questionFile.setCreatedBy(profile.getEmail());
					questionFileList.add(questionFile);
				}
			}
			returnLesson.setQuestionFileList(questionFileList);
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
}
