
package com.nse.common.validator;

import lombok.SneakyThrows;
import org.passay.*;
import com.nse.common.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 12:03:03 pm 
 * Objective:
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	/**
	 * Here, we initialize any thing which is supposed to be consumed within isValid()
	 */
	@Override
	public void initialize(final ValidPassword arg0) {
	}

	/**
	 * The actual validation logic will be written here. This will return the validation state as a boolean.
	 */
	@SneakyThrows
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {

		// customizing validation messages which will be loaded from passay.properties
		Properties props = new Properties();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");
		props.load(inputStream);
		MessageResolver msgResolver = new PropertiesMessageResolver(props);
		// This is passy PasswordValidator which receives loaded msgResolver and the list of predefined validation rules. 
		// Here, we can include as many validation rules as we required. This way we prepare PasswordValidator rule.
		PasswordValidator passyPwdValidator = new PasswordValidator(msgResolver, Arrays.asList(

				// [1] length between 8 and 16 characters
				new LengthRule(8, 16),

				// [2] at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),

				// [3] at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),

				// [4] at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),

				// [5] at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 1),

				// [6] no whitespace
				new WhitespaceRule(),

				// [7] rejects passwords that contain a sequence of >= 5 characters alphabetical (e.g. abcdef)
				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
				// [8] rejects passwords that contain a sequence of >= 5 characters numerical
				// (e.g. 12345)
				new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)));
		// Now, we will call validate() of PasswordValidator which will validate all the
		// validation rules that has been included in the prepared array list. This list was passed in 
		// PasswordValidator constructor.
		RuleResult result = passyPwdValidator.validate(new PasswordData(password));

		if (result.isValid()) {// This will return validation state which was made by validate(). It will
								// return whichever rules are failed.
			return true;
		}
		// The control will come under this block of code when there is a failure of any validation rule.
		List<String> messages = passyPwdValidator.getMessages(result);// It will return all the validation rule failed messages.
		String messageTemplate = String.join(",", messages);// Now, prepare a comma separated string containing all the
															// collected messages.

		// Now, we will prepare the validation details within context.
		context.buildConstraintViolationWithTemplate(messageTemplate)// prepare validation message is a predefined template
				.addConstraintViolation()// Add whichever validation rule are failed
				.disableDefaultConstraintViolation();// Disable default constraint violation

		return false;// This is the final return of validation
	}
}