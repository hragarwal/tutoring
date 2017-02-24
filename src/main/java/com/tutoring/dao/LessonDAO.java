package com.tutoring.dao;

import com.tutoring.model.Lesson;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LessonDAO extends CrudRepository<Lesson,Long> {
	
}
