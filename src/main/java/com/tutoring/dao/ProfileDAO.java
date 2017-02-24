package com.tutoring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.Profile;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface ProfileDAO extends CrudRepository<Profile,Long> {
	
	/**
	 * Returns the profile details for specified email address.
	 * @param email - profile email address
	 * @return Profile details for the provided email address
	 */
    public Profile findByEmail(String email);
}
