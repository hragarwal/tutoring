package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "USER_SUPPORT")
public class UserSupport extends AuditableBaseEntity {

	@Column(nullable = false)
    private long userId;
	
	@Column(nullable = false)
    private String email;
	
	@Column(nullable = false)
    private String title;
	
	@Lob
	@Column(nullable = false)
    private String message;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
