package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.LessonAnswer;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LessonAnswerDAO extends CrudRepository<LessonAnswer,Long> {
}
