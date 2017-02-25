package com.tutoring.service;

import java.util.List;

import com.tutoring.exception.AppException;
import com.tutoring.model.Subject;

public interface SubjectService {

	/**
	 * Returns the all subject.
	 * @return list of subjects
	 * @throws AppException
	 */
    public List<Subject> getAllSubjects() throws AppException;
}
