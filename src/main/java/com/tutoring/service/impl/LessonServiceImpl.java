package com.tutoring.service.impl;

import com.tutoring.dao.LessonDAO;
import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.dao.SubjectDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.service.LessonService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.LessonStates;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonDAO lessonDAO;

	@Autowired
	private LessonStatusDAO lessonStatusDAO;

	@Autowired
	private SubjectDAO subjectDAO;

	@Value("${file.save.location}")
	private String directory;

	public boolean createLesson(Lesson lesson, long studentId) throws AppException {
		try {
			lesson.setSubject(subjectDAO.findOne(Long.valueOf(lesson.getSubjectID())));
			lesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.AVAILABLE)));
			Lesson returnLesson = lessonDAO.save(lesson);
			FileUtils
					.copyDirectory(new File(directory + AppConstants.PROFILE + AppConstants.FORWARD_SLASH + studentId),
							new File(directory + AppConstants.LESSON + AppConstants.FORWARD_SLASH + returnLesson.getId()));
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
	public List<Lesson> getLessonsByStatus(long lessonStatus) throws AppException {
		return lessonDAO.getLessonsByLessonStatus(lessonStatus);
	}
}
