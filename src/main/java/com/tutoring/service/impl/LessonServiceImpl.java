package com.tutoring.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.LessonDAO;
import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.dao.SubjectDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.service.LessonService;
import com.tutoring.util.LessonStates;
import com.tutoring.util.ResponseVO;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonDAO lessonDAO;

	@Autowired
	private LessonStatusDAO lessonStatusDAO;

	@Autowired
	private SubjectDAO subjectDAO;

	public ResponseVO createLesson(Lesson lesson) throws AppException {
		lesson.setSubject(subjectDAO.findOne(Long.valueOf(lesson.getSubjectID())));
		lesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.AVAILABLE)));
		lessonDAO.save(lesson);
		return null;
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

}
