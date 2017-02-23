package com.tutoring.util;

/**
 * The Class AppUtils contains the utility method for application.
 */
public class AppUtils {

	
	/**
	 * Checks if is blank.
	 *
	 * @param text the text
	 * @return true, if is blank
	 */
	public boolean isBlank(String text) {
		return text == null || text.trim().equals("");
	}
	
	/**
	 * Checks if is not blank.
	 *
	 * @param text the text
	 * @return true, if is not blank
	 */
	public boolean isNotBlank(String text) {
		return !isBlank(text);
	}
	
}
