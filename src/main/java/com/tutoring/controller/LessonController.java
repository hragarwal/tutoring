package com.tutoring.controller;

import com.tutoring.model.Lesson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@RestController
@RequestMapping("/api/lesson/")
public class LessonController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createLesson(@RequestBody Lesson lesson){

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Lesson getLesson(@PathVariable(name = "id") long id){
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateLesson(@RequestBody Lesson lesson){

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Lesson> getAllLesson(){
        return null;
    }
}
