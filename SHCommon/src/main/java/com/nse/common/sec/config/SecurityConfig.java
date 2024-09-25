package com.nse.common.sec.config;

import java.util.List;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import com.nse.common.sec.filters.JwtAuthFilter;

/**
 * @author sanjeevkumar 
 * 24-July-2024 
 * 9:34:18 pm 
 * Objective: This is a main security class which has been annotated with three annotation and their inbuilt features.
 * @Configuration : It allows you to define beans within this class.
 * @EnableWebSecurity : It enables inbuilt feature of web security which can be configured for web url only.
 * @EnableMethodSecurity : It includes method level security. We can add role over a method to restrict the access even if the user
 *                       is authenticated and not having rights of authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
	MultiSourceAuthenticationProvider multiSourceAuthenticationProvider;
	
	

	
	
	private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", 
			                                        "/v2/api-docs",
			                                        "/pub/v1/**", 
			                                        "/auth/v1/login", 
			                                        "/auth/v1/refreshToken","/auth/v1/**"};

	/*
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:8005"));
		configuration.setAllowedMethods(List.of("GET", "POST"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}*/

	// Configuring HttpSecurity
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		             http
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(req ->req.requestMatchers(WHITE_LIST_URL).permitAll()
                        //.requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        //.requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        //.requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        //.requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        //.requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                        .anyRequest()
                        .authenticated()
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                        .authenticationProvider(multiSourceAuthenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                        /*.logout(logout ->logout.logoutUrl("/api/v1/auth/logout")
                                           .addLogoutHandler(logoutHandler)
                                           .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
                        */


                 return http.build();
	}
	
	   @Bean
	    public PasswordEncoder passwordEncoder() { 
	        return new BCryptPasswordEncoder(); 
	    } 
	     
	    @Bean()
	    public UserDetailsService userDetailsService() { 
	        return new LocalUserDetailsService(); 
	    } 
	    /* 
	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() { 
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
	        authenticationProvider.setUserDetailsService(userDetailsService()); 
	        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
	        return authenticationProvider; 
	    } */

	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}// End of SecurityConfig
