package com.tutoring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.model.Profile;

import java.util.Map;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LoginService {

    public Map<String,String> validateUser(Profile profile) throws JsonProcessingException;
}
