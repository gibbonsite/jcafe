package com.poleschuk.cafe.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * PasswordEncryption utility class helps encrypt passwords.
 */
public class PasswordEncryption {
	
	/**
	 * Encrypt password.
	 * 
	 * @param password
	 * @return String, encrypted password
	 */
    public static String md5Apache(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
