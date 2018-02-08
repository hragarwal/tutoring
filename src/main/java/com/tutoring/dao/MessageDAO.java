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
	 * @param lessonUniqueId - lesson id
	 * @return list of all messages for lessons
	 */
	@Query("select m from Message m where lesson.lessonUniqueId=:lessonUniqueId and receiverProfile.id is not null")
	public List<Message> getMessagesByLessonUniqueId(@Param("lessonUniqueId") String lessonUniqueId);

	@Query("select m from Message m where lesson.lessonUniqueId=:lessonUniqueId and receiverProfile.id is null")
	public List<Message> getMessagesByLessonUniqueIdAndAvailableStatus(@Param("lessonUniqueId") String lessonUniqueId);
	
	@Query("select count(m.id) from Message m WHERE lesson.id=:lessonId and receiverProfile.id is not null")
	public List<Message> getMessageCountByLessonId(@Param("lessonId") long lessonId);
	
	@Query("select count(m.id) from Message m WHERE lesson.id=:lessonId and receiverProfile.id is null")
	public List<Message> getAvailableMessageCountByLessonId(@Param("lessonId") long lessonId);
}
