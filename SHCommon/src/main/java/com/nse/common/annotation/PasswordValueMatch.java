
package com.nse.common.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nse.common.validator.PasswordFieldsValueMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 11:51:41 am 
 * Objective: This annotation will be used for matching two passwords 
 */

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordFieldsValueMatchValidator.class)
@Documented
public @interface PasswordValueMatch {
	
	String message() default "Fields values don't match!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field();//It will hold password

	String fieldMatch();//It will hold confirmed password

	//
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		PasswordValueMatch[] value();
	}
}
