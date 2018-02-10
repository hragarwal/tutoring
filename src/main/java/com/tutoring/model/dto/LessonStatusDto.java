package com.tutoring.model.dto;

import com.tutoring.dao.LessonStatusDAO;
import com.tutoring.model.Lesson;
import com.tutoring.model.LessonStatus;

import java.io.Serializable;

/**
 * Created by NEX3JCE on 2/10/2018.
 */
public class LessonStatusDto implements Serializable {

    private long id;

    private String name;

    public LessonStatusDto(LessonStatus lessonStatus) {
        this.id = lessonStatus.getId();
        this.name = lessonStatus.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
