package com.tutoring.dao;

import com.tutoring.model.Files;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface FilesDAO extends CrudRepository<Files,Long> {
}
