package com.tutoring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.LessonDAO;
import com.tutoring.dao.LessonStatusDAO;
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
	
	public ResponseVO createLesson(Lesson lesson) throws AppException {
		lesson.setStatus(lessonStatusDAO.findOne(Long.valueOf(LessonStates.AVAILABLE)));
		lessonDAO.save(lesson);
		return null;
	}
	
	@Override
	public List<Lesson> getAllLessons() throws AppException {
		return (List<Lesson>) lessonDAO.findAll();
	}

	@Override
	public List<Lesson> getLessonsByStudentProfile() throws AppException {
		return (List<Lesson>) lessonDAO.findAll();
	}

}
