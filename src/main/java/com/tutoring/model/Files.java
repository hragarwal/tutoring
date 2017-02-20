package com.tutoring.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "FILES")
public class Files extends AuditableBaseEntity {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
