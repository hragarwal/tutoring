package com.tutoring.dao;

import com.tutoring.model.LessonAnswer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LessonAnswerDAO extends CrudRepository<LessonAnswer,Long> {
}
