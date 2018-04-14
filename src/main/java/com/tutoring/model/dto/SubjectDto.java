package com.tutoring.model.dto;

import com.tutoring.model.Subject;
import com.tutoring.model.SubjectName;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by NEX3JCE on 2/10/2018.
 */
public class SubjectDto implements Serializable {

    private long id;

    private String name;

    private Set<SubjectNameDto> subjectNameDtos;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.subjectNameDtos = createSubjectNameList(subject.getSubjectNames());
    }

    private Set<SubjectNameDto> createSubjectNameList(Set<SubjectName> subjectNames){
        Set<SubjectNameDto> subjectNameDtos = new HashSet<>();
        if(Objects.nonNull(subjectNameDtos)){
            subjectNames.forEach(subjectName -> subjectNameDtos.add(new SubjectNameDto(subjectName)));
        }
        return subjectNameDtos;
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
