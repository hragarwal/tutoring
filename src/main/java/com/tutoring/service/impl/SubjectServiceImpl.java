package com.tutoring.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.SubjectDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Subject;
import com.tutoring.service.SubjectService;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;
	
	@Override
	public List<Subject> getAllSubjects() throws AppException {
		Iterator<Subject> profileIterable =  subjectDAO.findAll().iterator();
		return IteratorUtils.toList(profileIterable);
	}


}
