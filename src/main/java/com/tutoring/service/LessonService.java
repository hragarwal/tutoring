package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;

import java.util.List;

public interface LessonService {
	
	/**
	 * Create new lesson.
	 * @param lesson - lesson details
	 * @return response for newly submitted lesson
	 * @throws AppException
	 */
	public boolean createLesson(Lesson lesson, long studentId) throws AppException;
	
	/**
	 * Returns list of all past lessons.
	 * @return list of all lessons
	 * @throws AppException
	 */
	public List<Lesson> getAllLessons() throws AppException;
	
	
	/**
	 * Returns list of all past lessons for profile.
	 * @return list of all lesson for specific student
	 * @throws AppException
	 */
	public List<Lesson> getLessonsByProfile(long profileId) throws AppException;
	
	/**
	 * Returns lesson details by lesson id.
	 * @return lesson details for specific lesson id.
	 * @throws AppException
	 */
	public Lesson getLessonsByLessonId(long lessonId) throws AppException;
	
	/**
	 * Returns the list of lesson 
	 * @param lessonStatus - status of lesson
	 * @return List of all lesson with specified status
	 * @throws AppException
	 */
	public List<Lesson> getLessonsByStatus(long lessonStatus) throws AppException;
	
}
