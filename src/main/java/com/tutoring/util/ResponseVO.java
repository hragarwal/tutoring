package com.tutoring.util;

import java.io.Serializable;

/**
 * The Class ResponseVO.
 */
public class ResponseVO implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2095436668736100177L;

	/** The status. */
	private int status;

	/** The data. */
	private Object data;
	
	/** The access token. */
	private String accessToken;
	
	/** The title. */
	private String title;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new response VO.
	 */
	public ResponseVO() {
	
	}
	
	/**
	 * Instantiates a new response VO.
	 *
	 * @param status the status
	 * @param title the title  
	 * @param message the message
	 */
	public ResponseVO(int status, String title, String message) {
		this.status = status;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Instantiates a new response VO.
	 *
	 * @param status the status
	 * @param title the title
	 * @param message the message
	 * @param data the data - send data as response
	 * @param accessToken the access token
	 */
	public ResponseVO(int status, String title, String message, Object data, String accessToken) {
		this.status = status;
		this.title = title;
		this.message = message;
		this.data = data;
		this.accessToken = accessToken;
	}



	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 *
	 * @param accessToken the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
}
