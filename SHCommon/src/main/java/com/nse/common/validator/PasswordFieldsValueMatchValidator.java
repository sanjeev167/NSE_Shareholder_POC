
package com.nse.common.validator;

import org.springframework.beans.BeanWrapperImpl;

import com.nse.common.annotation.PasswordValueMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 12:07:02 pm 
 * Objective:
 */
public class PasswordFieldsValueMatchValidator implements ConstraintValidator<PasswordValueMatch, Object> {

	private String field;
	private String fieldMatch;
	private String message;

	/**
	 * Initialize the required fields
	 */
	public void initialize(PasswordValueMatch constraintAnnotation) {
		this.field = constraintAnnotation.field();//Taking out field value which will be used for comparing
		this.fieldMatch = constraintAnnotation.fieldMatch();//Taking out fieldMatch value which will be used for comparing with field value
		this.message = constraintAnnotation.message();// This is a default message  
	}

	/**
	 * This is the place where the actual validation logic will be written
	 */
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

		boolean isValid = false;
		if (fieldValue != null) {
			isValid = fieldValue.equals(fieldMatchValue);// Matching both values
		}
		if (!isValid) {//Now, prepare the context.
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldMatch).addConstraintViolation();
		}

		return isValid;
	}
}