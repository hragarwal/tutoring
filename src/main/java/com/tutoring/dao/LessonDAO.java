package com.tutoring.dao;

import java.util.List;

import com.tutoring.model.LessonStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tutoring.model.Lesson;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LessonDAO extends CrudRepository<Lesson,Long> {


	/**
	 *
	 * @param profileId - profile id of user to fetch lesson
	 * @return
	 */
	@Query("select l from Lesson l where studentProfile.id=:profileId OR tutorProfile.id=:profileId")
	public List<Lesson> getLessonsByProfileID(@Param("profileId") long profileId);

	/**
	 * Fetch only the available lesson list.
	 * @param lessonStatus - profile id of user to fetch lesson
	 * @return
	 */
	@Query("select l from Lesson l where status.id=:lessonStatus")
	public List<Lesson> getAvailableLessons(@Param("lessonStatus") long lessonStatus);

	/**
	 *
	 * @param lessonId
	 * @return
	 */
	@Query("select l.status from Lesson l where l.id=:lessonId")
	public LessonStatus getLessonStatus(@Param("lessonId") long lessonId);
	
}
