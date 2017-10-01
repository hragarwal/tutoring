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
	public static long AVAILABLE = 1;

	/** The accepted. */
	public static long ACCEPTED = 2;

	/** The rejected. */
	public static long REJECTED = 4;

	/** The in progress. */
	public static long IN_PROGRESS = 8;

	/** The waiting payment. */
	public static long WAITING_PAYMENT = 16;
	
	/** The waiting payment. */
	public static long PAYMENT_MADE = 32;

	/** The submitted. */
	public static long SUBMITTED = 64;

	/** The completed. */
	public static long COMPLETED = 128;

	/** The cancelled. */
	public static long CANCELLED = 256;
	
	/** The expired. */
	public static long EXPIRED = 512;

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
	
	/** The  payment made. */
	public static String _PAYMENT_MADE = "Payment Made";

	/** The  submitted. */
	public static String _SUBMITTED = "Submitted";

	/** The  completed. */
	public static String _COMPLETED = "Completed";

	/** The  cancelled. */
	public static String _CANCELLED = "Cancelled";
	
	/** The  expired. */
	public static String _EXPIRED = "Expired";

	/** The states. */
	private static Map<Long, String> states = new HashMap<>();

	static {
		states.put(AVAILABLE, _AVAILABLE);
		states.put(ACCEPTED, _ACCEPTED);
		states.put(REJECTED, _REJECTED);
		states.put(IN_PROGRESS, _IN_PROGRESS);
		states.put(WAITING_PAYMENT, _WAITING_PAYMENT);
		states.put(PAYMENT_MADE, _PAYMENT_MADE);
		states.put(SUBMITTED, _SUBMITTED);
		states.put(COMPLETED, _COMPLETED);
		states.put(CANCELLED, _CANCELLED);
		states.put(EXPIRED, _EXPIRED);
	}

	/**
	 * Gets the lesson states.
	 *
	 * @param key the key
	 * @return the lesson states
	 */
	public static String getLessonStates(long key){
		return states.get(key);
	}
	
	/**
	 * Gets the all lesson states.
	 *
	 * @return the all lesson states
	 */
	public static Map<Long, String> getAllLessonStates() {
		return states;
	}
	
	/**
	 * Checks lesson states.
	 *
	 * @param lessonStates status of lesson
	 * @param allowedStates the allowed states
	 * @return true, if is role accessible
	 */
	public static boolean isLessonStates(long lessonStates, long allowedStates){
		return (lessonStates & allowedStates) > 0 ? true : false;
	}
	
}
