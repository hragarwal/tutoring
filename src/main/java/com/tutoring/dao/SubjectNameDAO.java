package com.tutoring.dao;

import com.tutoring.model.SubjectName;
import org.springframework.data.repository.CrudRepository;

public interface SubjectNameDAO  extends CrudRepository<SubjectName,Long> {
}
