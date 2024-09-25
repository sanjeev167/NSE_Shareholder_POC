package com.nse.email.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.nse.email")
public class OneTimePasswordConfigurationProperties {

    private OTP otp = new OTP();

    @Data
    public class OTP {
        private Integer expirationMinutes;
    }

}