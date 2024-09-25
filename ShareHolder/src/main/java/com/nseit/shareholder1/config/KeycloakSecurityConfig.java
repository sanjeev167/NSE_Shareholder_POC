package com.nseit.shareholder1.config;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;

import com.nseit.shareholder1.filter.CustomSecurityFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	@Autowired
	CustomSecurityFilter customSecurityFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("in config");
		// super.configure(http);
		http.cors().and().csrf().disable().logout().and().authorizeRequests()
				.antMatchers("**.js", "/#**", "**.css", "/v2/api-docs", "/swagger-ui.html", "/webjars/**",
						"/swagger-resources/**", "/#/**",
						"**.js", "**.css", "**.html", "**.woff", "**.woff2", "/assets/**", "/**.ttf", "/**.ico",
						"/**.png", "/",
						"#")
				.permitAll()
				.and().authorizeRequests()
				.antMatchers("/", "/anonymous", "/shareholder/*")
				.permitAll()
				.anyRequest().permitAll();
		// /shareholder/login/*","/shareholder/checkLoginOtp/*","/shareholder/checkUserClientId/*","/shareholder/register/*","/shareholder/checkUserClientEmailAndPhone/*","/shareholder/OTPverify/*","/shareholder/checkUserSecurityQuestion/*","/shareholder/setSecurityQuestions/*","/shareholder/checkUserId/*","/shareholder/resetPassword/*
		// .antMatchers("/test/member-api").hasAnyRole("verifier")
		// .antMatchers("/test/admin").hasAnyRole("initiator").antMatchers("/test/all-user")
		// .hasAnyRole("authorizedrep").anyRequest().authenticated().and().authorizeRequests();
		// .permitAll();
		// http.csrf().disable();
		http.addFilterAfter(
				customSecurityFilter, BasicAuthenticationFilter.class);

	}

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// KeycloakAuthenticationProvider keycloakAuthenticationProvider =
	// keycloakAuthenticationProvider();
	// keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new
	// SimpleAuthorityMapper());
	// auth.authenticationProvider(keycloakAuthenticationProvider);
	// }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		// What this does is maps Keycloak X role to Spring Security ROLE_x role.
		SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
		// grantedAuthorityMapper.setPrefix("ROLE_");

		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	// @Override
	// protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	// return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	// }

	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {

		/**
		 * Returning NullAuthenticatedSessionStrategy means app will not remember
		 * session
		 */

		return new NullAuthenticatedSessionStrategy();
	}

	@Bean
	public FilterRegistrationBean<?> keycloakAuthenticationProcessingFilterRegistrationBean(
			KeycloakAuthenticationProcessingFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakAuthenticatedActionsFilterBean(KeycloakAuthenticatedActionsFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakSecurityContextRequestFilterBean(
			KeycloakSecurityContextRequestFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);

		return registrationBean;
	}

	@Bean
	@Override
	@ConditionalOnMissingBean(HttpSessionManager.class)
	protected HttpSessionManager httpSessionManager() {
		return new HttpSessionManager();
	}

	// @Bean
	// public KeycloakConfigResolver KeycloakConfigResolver() {
	// return new KeycloakSpringBootConfigResolver();
	// }
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("*");
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "HEAD"));
		corsConfig.addAllowedHeader("*");
		corsConfig.addExposedHeader("Content-Disposition");
		// corsConfig.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", corsConfig);

		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/#/**",
				"**.js", "**.css", "**.html", "**.woff", "**.woff2", "/assets/**", "/**.ttf", "/**.ico", "/**.png", "/",
				"#");
	}
}
