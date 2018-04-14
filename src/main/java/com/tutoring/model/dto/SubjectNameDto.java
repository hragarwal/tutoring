package com.tutoring.model.dto;

import com.tutoring.model.SubjectName;

import java.io.Serializable;

public class SubjectNameDto implements Serializable {

    private long id;

    private String name;

    public SubjectNameDto(SubjectName subjectName) {
        this.id = subjectName.getId();
        this.name = subjectName.getName();

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
