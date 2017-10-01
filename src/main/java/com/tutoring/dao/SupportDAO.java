package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.UserSupport;

/**
 * Created by chirag.agarwal on 01-10-2017
 */
public interface SupportDAO extends CrudRepository<UserSupport,Long> {
	
}
