
package com.nse.common.annotation;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// Here, we will tell PasswordValueMatch annotation that at which fields this will work. This is a class level custom validation which will work after filed
// level validation
@PasswordValueMatch.List({
    @PasswordValueMatch(
            field = "password",
            fieldMatch = "confirmPassword",
            message = "Passwords do not match!" 
    )
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
/**
 * sanjeevkumar 
 * 02-Aug-2024 
 * 11:56:43 am
 * Objective: 
 */
public class UserData {
	@NonNull
    @NotBlank(message = "username is mandatory")
    private String username;

    @NotNull
    @NotEmpty
    @Email
    private String email;


    @ValidPassword
    @NonNull
    @NotBlank(message = "New password is mandatory")
    private String password;


    @ValidPassword
    @NonNull
    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;
}
