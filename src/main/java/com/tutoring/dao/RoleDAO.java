package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.Role;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface RoleDAO extends CrudRepository<Role,Long>{
	
	
	
	/**
	 * Find the role details for specified role string value.
	 *
	 * @param name - role string name 
	 * @return the role 
	 */
	public Role findByName(String name);
}
