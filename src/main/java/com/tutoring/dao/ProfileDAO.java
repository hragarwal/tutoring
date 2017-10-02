package com.tutoring.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tutoring.model.Profile;
import org.springframework.data.repository.query.Param;

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
    
    /**
	 * Returns the profile details for specified username.
	 * @param username - profile username
	 * @return Profile details for the provided username
	 */
    public Profile findByUsername(String username);
    
    /**
     * Allow login using both either the username or emailAddress
     * @param username
     * @return
     */
	@Query("select p from Profile p where p.username=:username OR p.email=:username")
    public Profile authenticateUser(@Param("username") String username);
}
