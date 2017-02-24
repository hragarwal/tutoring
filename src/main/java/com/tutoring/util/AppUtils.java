package com.tutoring.util;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class AppUtils contains the utility method for application.
 */
public class AppUtils {


	/** The exclude URL. */
	private static String [] excludeURL = { Mappings.HOME, Mappings.LOGIN, Mappings.PROFILE};

	/**
	 * Checks if is go to login.
	 *
	 * @param request the request
	 * @return true, if is go to login
	 */
	public static boolean isGoToLogin(HttpServletRequest request){
		String uri = request.getRequestURI();
		for(String urlPart : excludeURL){
			if(uri.contains(urlPart))
				return true;
		}
		return false;
	}


	/**
	 * Checks if is blank.
	 *
	 * @param text the text
	 * @return true, if is blank
	 */
	public static boolean isBlank(String text) {
		return text == null || text.trim().equals("");
	}

	/**
	 * Checks if is not blank.
	 *
	 * @param text the text
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(String text) {
		return !isBlank(text);
	}

}
