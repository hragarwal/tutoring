package com.tutoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.model.Profile;

import java.util.Map;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public interface LoginService {

     Map<String,String> validateUser(Profile profile) throws JsonProcessingException;
}
