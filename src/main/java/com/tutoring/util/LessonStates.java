package com.tutoring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class holds the constants for possible states of lesson entity.
 * 
 * @author CHIRAG
 * 
 */
public class LessonStates {

	/**  lesson requested. */
	public static int AVAILABLE = 1;

	/** The accepted. */
	public static int ACCEPTED = 2;

	/** The rejected. */
	public static int REJECTED = 4;

	/** The in progress. */
	public static int IN_PROGRESS = 8;

	/** The waiting payment. */
	public static int WAITING_PAYMENT = 16;

	/** The submitted. */
	public static int SUBMITTED = 32;

	/** The completed. */
	public static int COMPLETED = 64;

	/** The cancelled. */
	public static int CANCELLED = 128;

	/** The  available. */
	public static String _AVAILABLE = "Available";

	/** The  accepted. */
	public static String _ACCEPTED = "Accepted";

	/** The  rejected. */
	public static String _REJECTED = "Rejected";

	/** The  in progress. */
	public static String _IN_PROGRESS = "In Progress";

	/** The  waiting payment. */
	public static String _WAITING_PAYMENT = "Waiting Payment";

	/** The  submitted. */
	public static String _SUBMITTED = "Submitted";

	/** The  completed. */
	public static String _COMPLETED = "Completed";

	/** The  cancelled. */
	public static String _CANCELLED = "Cancelled";

	/** The states. */
	private static Map<Integer, String> states = new HashMap<>();

	static {
		states.put(AVAILABLE, _AVAILABLE);
		states.put(ACCEPTED, _ACCEPTED);
		states.put(REJECTED, _REJECTED);
		states.put(IN_PROGRESS, _IN_PROGRESS);
		states.put(WAITING_PAYMENT, _WAITING_PAYMENT);
		states.put(SUBMITTED, _SUBMITTED);
		states.put(COMPLETED, _COMPLETED);
		states.put(CANCELLED, _CANCELLED);
	}

	/**
	 * Gets the lesson states.
	 *
	 * @param key the key
	 * @return the lesson states
	 */
	public static String getLessonStates(int key){
		return states.get(key);
	}
	
	/**
	 * Gets the all lesson states.
	 *
	 * @return the all lesson states
	 */
	public static Map<Integer, String> getAllLessonStates() {
		return states;
	}
	
}
