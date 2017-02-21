package com.tutoring.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
public class PasswordUtil {

    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 12;

    /**
     * This method encrypts the password to be stored in database
     * @param password
     * @return hashed password
     */
    public static String hashPassword(String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(workload));
        return hashedPassword;
    }

    public static boolean verifyPassword(String plainPassword, String hashPassword){
        return BCrypt.checkpw(plainPassword,hashPassword);
    }
}
