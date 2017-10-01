package com.tutoring.util;

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
import java.util.stream.Collectors;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutoring.model.LessonStatus;
import com.tutoring.model.Profile;

/**
 * The Class AppUtils contains the utility method for application.
 */
public class AppUtils {

	private static Pattern pattern;
	
	private static Pattern usernamePattern; 

	private static int PASSWORD_MIN_LENGTH = 6;
	
	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,}$";


	/** The exclude URL. */
	private static String [] excludeURL = { Mappings.LOGIN, Mappings.SIGN_UP,
			 Mappings.JS_URL, Mappings.CSS_URL, Mappings.HTML_URL, Mappings.FORGOT_PASSWORD};


	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	static {
		pattern = Pattern.compile("[a-zA-Z0-9_.+-]+\\s*@+\\s*[a-zA-Z0-9-]+\\s*\\.+\\s*[a-zA-Z0-9-.]+");
		usernamePattern = Pattern.compile(USERNAME_PATTERN);
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
		/** for images type */
		for(String urlPart : Mappings.IMAGES_URL){
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

	public static void prepareFileAndFlushResponse(HttpServletResponse response, byte[] bytes, String filename) throws IOException{
		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		response.setContentType(mimetypesFileTypeMap.getContentType(filename));
		response.setContentLength(bytes.length);
		FileCopyUtils.copy(bytes, response.getOutputStream());
	}

	/**
	 * Returns true if username is valid or otherwise returns false.
	 * @param username - username string
	 * @return
	 */
	public static boolean isValidUsername(String username) {
		return usernamePattern.matcher(username).matches();
	}
	
	/**
	 * Returns true if email is valid or otherwise returns false.
	 * @param email - email string
	 * @return
	 */
	public static boolean isValidEmailAddress(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		return pattern.matcher(email).matches();
	}

	/**
	 * Returns the profile details
	 * @param profile - user profile details
	 * @return
	 */
	public static boolean valiateProfile(Profile profile) {
		if(Objects.isNull(profile)) {
			return false;
		}
		else if(AppUtils.isBlank(profile.getPassword()) || profile.getPassword().length() < PASSWORD_MIN_LENGTH ||
				AppUtils.isBlank(profile.getUsername()) || !isValidUsername(profile.getUsername()) ||
				AppUtils.isBlank(profile.getEmail()) || !isValidEmailAddress(profile.getEmail()) ||
				AppUtils.isBlank(profile.getName()) ||
				AppUtils.isBlank(profile.getCountry()) || 
				AppUtils.isBlank(profile.getCreatedBy())) {
			return false;
		}
		return true;
	}

	/**
	 * Return the unique file name append the time stamp with file
	 * @param filename - file name 
	 * @return unique file name
	 */
	public static String getUniqueFilename(String filename){
		int dotIndex = filename.lastIndexOf(".");
		String fileNameWithoutExt = filename.substring(0, dotIndex);
		String extension = filename.substring(dotIndex,filename.length());
		return fileNameWithoutExt + AppConstants.UNDERSCORE + DateTimeUtil.getCurrentDate().getTime() + extension;
	}

	/**
	 * Delete the file directory for user.
	 * @param profile - current profile details
	 * @param profileDirectory - file directory
	 * @throws IOException
	 */
	public static void deleteDirectoryForUser(Profile profile, String profileDirectory) throws IOException{
		File file = new File(profileDirectory + profile.getId());
		FileUtils.deleteDirectory(file);
	}

	/**
	 * Return actual file name 
	 * @param filename - file name 
	 * @return actual file name
	 */
	public static String getActualFilenameFromServerFile(String filename){
		int underScoreIndex = filename.lastIndexOf(AppConstants.UNDERSCORE);
		int dotIndex = filename.lastIndexOf(".");
		return filename.substring(0,underScoreIndex) + filename.substring(dotIndex,filename.length());
	}

	/**
	 * This method check if user is validate to perform action on lesson or not.
	 * @param currentProfileRoleId - user role id of current profile
	 * @param updateLessonStatus - status to be update for lesson
	 * @return true if user is allowed to perform the action otherwise false.
	 */
	public static boolean isAccessible(long currentProfileRoleId, long updateLessonStatus) {

		if(updateLessonStatus == LessonStates.ACCEPTED && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.TUTOR)) {
			return true;
		}else if(updateLessonStatus == LessonStates.IN_PROGRESS && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.TUTOR)) {
			return true;
		}else if(updateLessonStatus == LessonStates.WAITING_PAYMENT && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.TUTOR)) {
			return true;
		}else if(updateLessonStatus == LessonStates.SUBMITTED && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.TUTOR)) {
			return true;
		}else if(updateLessonStatus == LessonStates.COMPLETED && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.STUDENT)) {
			return true;
		}else if(updateLessonStatus == LessonStates.CANCELLED && RoleStates.isRoleAccessible(currentProfileRoleId, RoleStates.STUDENT)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return list of all applicable list of lesson status for user.
	 * @param lessonStatus - applicable lesson status 
	 * @param currentRole - role of current user
	 * @return list of all lesson status available for user.
	 */
	public static List<LessonStatus> getAvailableLessonStatus(List<LessonStatus> lessonStatus, long currentRole) {
		return lessonStatus.stream().filter(status -> RoleStates.isRoleAccessible(currentRole, status.getAllowedRoles())).collect(Collectors.toList());
	}

	public static String getJSONValue(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
}
