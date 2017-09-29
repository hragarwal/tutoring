package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "LESSON_STATUS")
public class LessonStatus extends PersistableBaseEntity {

    @Column(nullable = false)
    private String name;
    
    private long allowedRoles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public long getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(long allowedRoles) {
		this.allowedRoles = allowedRoles;
	}
    
    
}
