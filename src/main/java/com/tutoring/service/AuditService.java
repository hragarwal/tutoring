package com.tutoring.service;

import com.tutoring.exception.AppException;
import com.tutoring.model.Audit;

/**
 * Created by himanshu.agarwal on 26-02-2017.
 */
public interface AuditService {

	public Audit save(Audit audit) throws AppException;

}
