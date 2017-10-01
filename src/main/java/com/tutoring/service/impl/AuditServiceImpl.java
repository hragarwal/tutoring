package com.tutoring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.AuditDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.Audit;
import com.tutoring.service.AuditService;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditDAO auditDAO;
	
	@Override
	public Audit save(Audit audit) throws AppException {
		return auditDAO.save(audit);
	}
}
