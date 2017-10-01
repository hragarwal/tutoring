package com.tutoring.model;

/**
 * This file contains the JSON structure for audit field content.
 * @author chirag.agrawal
 *
 */
public class AuditContent {

	private String fieldName;
	
	private String oldValue;
	
	private String newValue;
	
	private String message;

	public AuditContent() {
		
	}
	
	/**
	 * 
	 * @param fieldName - label name
	 * @param oldValue - old value 
	 * @param newValue new value
	 * @param message - message description.
	 */
	public AuditContent(String fieldName, String oldValue, String newValue, String message) {
		super();
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.message = message;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
