//package com.nseit.shareholder1.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.nseit.shareholder1.filter.JwtRequestFilter;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@EnableWebSecurity
//@Slf4j
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	/*
//	 * @Autowired private UserDetailsService myUserDetailsService;
//	 * 
//	 * @Bean public UserDetailsService userDetailsService() { return
//	 * super.userDetailsService(); }
//	 */
//
//	@Autowired
//	private JwtRequestFilter jwtRequestFilter;
//
//	@Autowired
//	private DataSource dataSource;
//
//	// @Autowired
//	// private CorsFilter corsFilter;
//
//	/*
//	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
//	 * throws Exception { auth.userDetailsService(myUserDetailsService); }
//	 */
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
//
////	@Override
////	@Bean
////	public AuthenticationManager authenticationManagerBean() throws Exception {
////		return super.authenticationManagerBean();
////	}
//
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		log.info("In configure Web Security");
//		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/shareholder/login/*","/shareholder/checkLoginOtp/*", "/shareholder/checkUserClientId/*","/shareholder/register/*","/shareholder/checkUserClientEmailAndPhone/*","/shareholder/OTPverify/*","/shareholder/checkUserSecurityQuestion/*","/shareholder/setSecurityQuestions/*","/shareholder/checkUserId/*","/shareholder/resetPassword/*","/actuator/health")
//				.permitAll().anyRequest().authenticated().and().exceptionHandling().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		httpSecurity.cors();
//		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		// .addFilterBefore(corsFilter, ChannelProcessingFilter.class);
//
//	}
//
////	@Bean
////	public WebMvcConfigurer configure() {
////		return new WebMvcConfigurer() {
////
////			@Override
////			public void addCorsMappings(CorsRegistry registry) {
////				registry.addMapping("/*").allowedOrigins("*").allowedMethods("GET", "POST").allowedHeaders("*");
////			}
////
////		};
////	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**");
//	}
//
//}
