
package com.nse.common.sec.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 10:48:29 am 
 * Objective:
 */
public class PasswordValidator {
	String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
	/*
	 * ############################## Breaking down its components:
	 * ##############################
	 * 
	 * ^: indicates the string’s beginning
	 * 
	 * (?=.*[a-z]): makes sure that there is at least one small letter
	 * 
	 * (?=.*[A-Z]): needs at least one capital letter
	 * 
	 * (?=.*\\d): requires at least one digit
	 * 
	 * (?=.*[@#$%^&+=]): provides a guarantee of at least one special symbol
	 * 
	 * .{8,20}: imposes the minimum length of 8 characters and the maximum length of
	 * 20 characters
	 * 
	 * $: terminates the string
	 * 
	 **/
	@NotNull
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private final String password;

	public PasswordValidator(String password) {
		this.password = password;
	}

	public boolean validatePasswordDynamically(String password) {

		boolean result = false;
		try {
			if (password != null) {
				String MIN_LENGTH = "8";
				String MAX_LENGTH = "20";
				boolean SPECIAL_CHAR_NEEDED = false;
				String ONE_DIGIT = "(?=.*[0-9])";
				String LOWER_CASE = "(?=.*[a-z])";
				String UPPER_CASE = "(?=.*[A-Z])";
				String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
				String NO_SPACE = "(?=\\S+$)";
				String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
				String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
				result = password.matches(PATTERN);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
