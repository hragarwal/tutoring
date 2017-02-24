package com.tutoring.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Enum MessageReader.
 */
public enum MessageReader {

	/** The reader. */
	READER();

	/** The properties. */
	private  Properties properties = null;

	/**
	 * Instantiates a new message reader.
	 */
	private MessageReader() {
		properties = new Properties();
		InputStream propStream =  getClass().getClassLoader()
				.getResourceAsStream(AppConstants.MESSAGE_PROPERTIES_FILE);
		if(propStream != null){
			try {
				properties.load(propStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the property value by key.
	 *
	 * @param key the key
	 * @return the property
	 */
	public String getProperty(String key){
		return properties.getProperty(key);	
	}
}
