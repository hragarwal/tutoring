package com.tutoring.model.dto;

import com.tutoring.model.Files;

import java.io.Serializable;

/**
 * Created by NEX3JCE on 2/10/2018.
 */
public class FileDto implements Serializable {

    private String path;

    private String type;

    private String name;

    public FileDto(Files file) {
        this.path = file.getFilePath();
        this.type = file.getFileType();
        this.name = file.getActualFileName();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
