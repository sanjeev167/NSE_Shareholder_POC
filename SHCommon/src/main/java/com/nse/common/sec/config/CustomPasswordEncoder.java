
package com.nse.common.sec.config;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 10:47:20 am
 * Objective: 
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public String encode(CharSequence rawPassword) {
        validatePassword(rawPassword.toString());
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    private void validatePassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(password);
        Set<ConstraintViolation<PasswordValidator>> violations = validator.validate(passwordValidator);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<PasswordValidator> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            throw new ConstraintViolationException(sb.toString().trim(), violations);
        }
    }
    
}
