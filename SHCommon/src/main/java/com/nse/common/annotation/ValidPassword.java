
package com.nse.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.nse.common.validator.PasswordConstraintValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 11:39:22 am
 * Objective: A ValidPassword annotation is created here which will validate a payload as password 
 * within a PasswordConstraintValidator class under its isValid() method.
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)//This constraint will be tackled by PasswordConstraintValidator
@Target({TYPE,FIELD,ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {
	
	String message() default "Invalid Password";//This is a default password validation message.	
	
    Class<?>[] groups() default {};	//
	
	Class<? extends Payload>[] payload() default {};//
}
