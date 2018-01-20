package com.tutoring.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tutoring.exception.AppException;
import com.tutoring.model.Lesson;
import com.tutoring.model.LessonStatus;
import com.tutoring.model.Profile;
import com.tutoring.util.ResponseVO;

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

	@Modifying
	@Query(nativeQuery = true,
			value = "update lesson set status_id=:newStatusId, modified_date=:currentDate " +
					"where deadline<=:currentDate and status_id in :statusList")
	public void updateExpiredLessons(@Param("currentDate")Date currentDate,
											 @Param("statusList") List<Long> statusList,
									 @Param("newStatusId") long newStatusId);
	
	/**
	 * This method used to get lesson list by profile and status.
	 * @param profile
	 * @param statusList
	 * @return
	 * @throws AppException
	 */
	@Query("select l from Lesson l where studentProfile.id=:profileId AND status_id in :statusList")
	public List<Lesson> getLessonByProfileAndStatus(@Param("profileId") long profileId,  @Param("statusList") List<Long> statusList) throws AppException;
	
}
