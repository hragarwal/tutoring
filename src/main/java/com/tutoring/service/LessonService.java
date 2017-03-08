package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.Profile;
import com.tutoring.util.ResponseVO;

import java.io.IOException;
import java.util.List;

public interface LessonService {
	
	/**
	 * Create new lesson.
	 * @param lesson - lesson details
	 * @return response for newly submitted lesson
	 * @throws AppException
	 */
	public boolean createLesson(Lesson lesson, Profile profile) throws AppException;
	
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
	 * Returns the list of available lesson. 
	 * @param lessonStatus - status of lesson
	 * @return List of all lesson with specified status
	 * @throws AppException
	 */
	public List<Lesson> getAvailableLessons(long lessonStatus) throws AppException;
	
	/**
	 * Update the lesson status 
	 * @param lesson - lesson details
	 * @param currentProfile - current profile details
	 * @return response of update call
	 * @throws AppException
	 */
	public ResponseVO updateLessonStatus(Lesson lesson, Profile currentProfile) throws IOException;
	
}
