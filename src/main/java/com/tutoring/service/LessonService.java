package com.tutoring.service;

import java.util.List;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.util.ResponseVO;

public interface LessonService {
	
	/**
	 * Create new lesson.
	 * @param lesson - lesson details
	 * @return response for newly submitted lesson
	 * @throws AppException
	 */
	public ResponseVO createLesson(Lesson lesson) throws AppException;
	
	/**
	 * Returns list of all past lessons.
	 * @return list of all lessons
	 * @throws AppException
	 */
	public List<Lesson> getAllLessons() throws AppException;
	
	
	/**
	 * Returns list of all past lessons for student profile.
	 * @return list of all lesson for specific student
	 * @throws AppException
	 */
	public List<Lesson> getLessonsByStudentProfile() throws AppException;
	
}
