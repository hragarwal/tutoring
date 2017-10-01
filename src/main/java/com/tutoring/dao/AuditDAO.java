package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.Audit;

/**
 * Created by chirag.agarwal on 01-10-2017.
 */
public interface AuditDAO extends CrudRepository<Audit,Long> {
	
}
