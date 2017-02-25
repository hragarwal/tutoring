package com.tutoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tutoring.model.Lesson;
import com.tutoring.service.LessonService;
import com.tutoring.service.SubjectService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.Mappings;
import com.tutoring.util.ResponseVO;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@RestController
public class LessonController extends AppController {

	@Autowired
	private LessonService lessonService;
	
    @RequestMapping(value = Mappings.NEW_LESSON, method = RequestMethod.POST)
    public ResponseVO createLesson(@RequestBody Lesson lesson) {
    	ResponseVO responseVO = null;
		try {
			responseVO = lessonService.createLesson(lesson);
		} catch (Exception e) {
			//throw new AppException(logger, msgKey, entityName)
			responseVO = new ResponseVO(AppConstants.ERROR, AppConstants.TEXT_ERROR, AppConstants.DEFAULT_ERROR_MESSAGE);
			e.getMessage();
		}
		return responseVO;
    }

    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Lesson getLesson(@PathVariable(name = "id") long id){
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateLesson(@RequestBody Lesson lesson){

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Lesson> getAllLesson(){
        return null;
    }*/
}
