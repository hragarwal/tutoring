package com.tutoring.exception;

/**
 * The Class AppException.
 */
public class AppException extends Exception {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8024336814604198538L;

	/** The message. */
	private String message;
	
	private Throwable nestedException;

	public AppException() {

	}
	
	public AppException(String msg) {
		this.message = msg;
	}
	
	public AppException(String msg, Throwable nestedException) {
		this.message = msg;
		this.nestedException = nestedException;
	}
	
	public Throwable getNestedException() {
		return nestedException;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	
}
