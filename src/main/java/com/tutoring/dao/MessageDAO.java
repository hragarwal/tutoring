package com.tutoring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tutoring.model.Message;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface MessageDAO extends CrudRepository<Message,Long> {
	
	/**
	 * Returns list of all messages for specified lesson id
	 * @param lessonId - lesson id
	 * @return list of all messages for lessons
	 */
	@Query("select m from Message m where lesson.id=:lessonId and receiverProfile.id is not null")
	public List<Message> getMessagesByLessonId(@Param("lessonId") long lessonId);

	@Query("select m from Message m where lesson.id=:lessonId and receiverProfile.id is null")
	public List<Message> getMessagesByLessonIdAndAvailableStatus(@Param("lessonId") long lessonId);
}
