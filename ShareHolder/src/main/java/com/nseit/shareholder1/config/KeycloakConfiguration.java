package com.nseit.shareholder1.config;

import javax.annotation.PostConstruct;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfiguration {

    private String authServerUrl;
    private String resource;
    private Credentials credentials;
    private String realm;
   

    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    public static class Credentials {
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        @Override
        public String toString() {
            return "Credentials [secret=" + secret + "]";
        }
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }
    
   

    @PostConstruct
    public void printConfig() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "KeycloakConfiguration [authServerUrl=" + authServerUrl + ", credentials=" + credentials + ", realm="
                + realm + ", resource=" + resource +  "]";
    }
}
