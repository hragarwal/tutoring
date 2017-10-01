package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "audit")
public class Audit extends AuditableBaseEntity {

    @Column(nullable = false, length = 2048)
    /* content will be in JSON format */
	private String content;
    
    @Column(nullable = false)
    private long lessonId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getLessonId() {
		return lessonId;
	}

	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}
    
    
}
