package com.tutoring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.dao.SupportDAO;
import com.tutoring.exception.AppException;
import com.tutoring.model.UserSupport;
import com.tutoring.service.SupportService;

@Service
@Transactional
public class SupportServiceImpl implements SupportService {

	@Autowired
	private SupportDAO supportDAO;
	
	@Override
	public UserSupport save(UserSupport support) throws AppException {
		support = supportDAO.save(support);
		return support;
	}
}
