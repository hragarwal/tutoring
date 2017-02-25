package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.Files;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface FilesDAO extends CrudRepository<Files,Long> {
}
