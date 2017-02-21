package com.tutoring.dao;

import com.tutoring.model.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface ProfileDAO extends CrudRepository<Profile,Long> {

    Profile findByEmail(String email);
}
