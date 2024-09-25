package com.nse.email.security.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nse.email.repository.UserRepository;
import com.nse.email.security.CustomUserDetailService;
import com.nse.email.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	@Autowired
	CustomUserDetailService customUserDetialService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", 
										            "/v2/api-docs",
										            "/pub/v1/**", 
										            "/auth/v1/login", 
										            "/auth/v1/refreshToken"};
		
	

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(new CustomUserDetailService(userRepository));
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(daoAuthenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();

	}

}