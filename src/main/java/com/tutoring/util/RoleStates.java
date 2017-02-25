package com.tutoring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class holds the constants for possible user roles of application.	
 * 
 * @author CHIRAG
 */
public class RoleStates {

	/** The super admin. */
	public static int SUPER_ADMIN = 1;

	/** The admin. */
	public static int ADMIN = 2;

	/** The support. */
	public static int SUPPORT = 4;

	/** The tutor. */
	public static int TUTOR = 8;

	/** The student. */
	public static int STUDENT = 16;

	/** The  super admin. */
	public static String _SUPER_ADMIN = "Super Admin";

	/** The  admin. */
	public static String _ADMIN = "Admin";

	/** The  support. */
	public static String _SUPPORT = "Support";

	/** The  tutor. */
	public static String _TUTOR = "Tutor";

	/** The  student. */
	public static String _STUDENT = "Student";

	/** The states. */
	private static Map<Integer, String> states = new HashMap<>();
	
	static {
		states.put(SUPER_ADMIN, _SUPER_ADMIN);
		states.put(ADMIN, _ADMIN);
		states.put(SUPPORT, _SUPPORT);
		states.put(TUTOR, _TUTOR);
		states.put(STUDENT, _STUDENT);
	}

	
	/**
	 * Gets the role.
	 *
	 * @param key the key
	 * @return the role
	 */
	public static String getRole(int key){
		return states.get(key);
	}
	
	/**
	 * Gets the all roles.
	 *
	 * @return the all roles
	 */
	public static Map<Integer, String> getAllRoles() {
		return states;
	}
	
	/**
	 * Checks if is role accessible.
	 *
	 * @param userRole the user role
	 * @param allowedRules the allowed rules
	 * @return true, if is role accessible
	 */
	public static boolean isRoleAccessible(int userRole, int allowedRules){
		return (userRole & allowedRules) > 0 ? true : false;
	}
	
}