package com.tutoring.dao;

import com.tutoring.model.LessonStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LessonStatusDAO extends CrudRepository<LessonStatus,Long> {
}
