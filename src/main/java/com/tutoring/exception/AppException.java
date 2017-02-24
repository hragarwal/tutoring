package com.tutoring.exception;

import org.apache.log4j.Logger;

/**
 * The Class AppException.
 */
public class AppException extends Exception {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8024336814604198538L;

	/** The message. */
	private String message;

	/** The nested exception. */
	private Throwable nestedException;
	
	/** The prop value. */
	String propValue = null;

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the nested exception.
	 *
	 * @return the nested exception
	 */
	public Throwable getNestedException() {
		return nestedException;
	}

	/**
	 * Instantiates a new app exception.
	 *
	 * @param logger the logger
	 * @param methodName the method name
	 * @param msgKey the msg key
	 * @param args the args
	 */
	public AppException(Logger logger, String methodName, String msgKey, Object[] args) {

	}

	/**
	 * Read property.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String readProperty(String key){
		return "";
	}

	/**
	 * Instantiates a new app exception.
	 *
	 * @param logger the logger
	 * @param methodName the method name
	 * @param msgKey the msg key
	 * @param args the args
	 * @param ex the ex
	 */
	public AppException(Logger logger, String methodName,
			String msgKey, String[] args, Exception ex) {
	}


	/**
	 * Instantiates a new app exception.
	 *
	 * @param logger the logger
	 * @param msgKey the msg key
	 * @param entityName the entity name
	 */
	public AppException(Logger logger, String msgKey, String entityName) {

	}
}
