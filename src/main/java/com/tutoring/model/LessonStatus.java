package com.tutoring.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "LESSON_STATUS")
public class LessonStatus extends PersistableBaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
