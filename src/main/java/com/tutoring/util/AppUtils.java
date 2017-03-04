package com.tutoring.util;

import com.tutoring.model.Profile;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class AppUtils contains the utility method for application.
 */
public class AppUtils {
	
	private static Pattern pattern;

	/** The exclude URL. */
	private static String [] excludeURL = { Mappings.LOGIN, Mappings.PROFILE,
			Mappings.IMAGE_URL, Mappings.JS_URL, Mappings.CSS_URL, Mappings.HTML_URL, Mappings.FORGOT_PASSWORD};
	
	static {
		pattern = Pattern.compile("[a-zA-Z0-9_.+-]+\\s*@+\\s*[a-zA-Z0-9-]+\\s*\\.+\\s*[a-zA-Z0-9-.]+");
	}

	/**
	 * Checks if is go to login.
	 *
	 * @param request the request
	 * @return true, if is go to login
	 */
	public static boolean isExcludedURL(HttpServletRequest request){
		String uri = request.getRequestURI();
		if(uri.equals(Mappings.DEFAULT_URL)){
			return true;
		}
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

	/**
	 * Return the details for current profile from session instance
	 * @param request - http servlet request 
	 * @return current profile details
	 */
	public static Profile getCurrentUserProfile(HttpServletRequest  request)  {
		Object object = request.getSession().getAttribute(AppConstants.PROFILE);
		if(Objects.nonNull(object)) 
			return (Profile) object;

		return null;
	}
	
	/**
	 * Return the profile id of current user
	 * @param request - http servlet request 
	 * @return current profile details
	 */
	public static long getCurrentUserProfileID(HttpServletRequest  request)  {
		Object object = request.getSession().getAttribute(AppConstants.PROFILE);
		if(Objects.nonNull(object)) {
			Profile profile =  (Profile) object;
				return profile.getId();
		}
		return 0;
	}

	/**
	 * Return list of email contains in message
	 * @param message - message text
	 * @return list of email strings 
	 */
	public static List<String> containsEmailInMessage(String message) {
		Matcher m = pattern.matcher(message);
		List<String> emails = new ArrayList<>();
		while (m.find()) {
			emails.add(m.group());
		}
		return emails;
	}

	public static byte[] getFileFromLocalFile(String artworkFileLocation) throws FileNotFoundException,IOException {
		File file = new File(artworkFileLocation);
		if (file.exists()) {
			InputStream inputStream = new FileInputStream(file);
			return IOUtils.toByteArray(inputStream);
		}
		return null;
	}

	public static void  prepareFileAndFlushResponse(HttpServletResponse response, byte[] bytes, String filename) throws IOException{
		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		response.setContentType(mimetypesFileTypeMap.getContentType(filename));
		response.setContentLength(bytes.length);
		FileCopyUtils.copy(bytes, response.getOutputStream());
	}
}
