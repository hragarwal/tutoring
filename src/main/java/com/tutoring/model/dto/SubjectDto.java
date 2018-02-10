package com.tutoring.model.dto;

import com.tutoring.model.Subject;

import java.io.Serializable;

/**
 * Created by NEX3JCE on 2/10/2018.
 */
public class SubjectDto implements Serializable {

    private long id;

    private String name;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
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
