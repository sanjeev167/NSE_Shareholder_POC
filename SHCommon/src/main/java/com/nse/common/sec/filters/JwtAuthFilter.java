/**
 * 
 */
package com.nse.common.sec.filters;

import java.io.IOException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nse.common.exception.InvalidJwtAuthenticationException;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.config.LocalUserDetailsService;
import com.nse.common.sec.service.JwtUtils;

/**
 * @author sanjeevkumar 
 * 23-Mar-2024 
 * 7:48:26 pm 
 * Objective: This class helps us to validate the generated jwt token
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private LocalUserDetailsService localUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtUtils.extractUsername(token);			
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {							
			LocalUserDetails localUserDetails = (LocalUserDetails) localUserDetailsService.loadUserByUsername(username.split("@")[0]);			
			try {
				if (jwtUtils.validateToken(token, localUserDetails)) { 
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(localUserDetails,
							null, localUserDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);				
					doContextLoggingConfiguration(localUserDetails);				
				}
			} catch (SignatureException e) {
				throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
			}
		 
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * @author sanjeevkumar 
	 * 23-Mar-2024 
	 * 1:48:26 pm 
	 * @param userInfoDetails
	 * Objective: 
	 */
	
	private void doContextLoggingConfiguration(LocalUserDetails localUserDetails) {
	    String dateFormatUsed = "dd-MM-yyyy";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormatUsed);
		LocalDateTime now = LocalDateTime.now();
		String apiCallReceivedOn = dtf.format(now);		
		MDC.clear();
		//System.out.println("Sanjeev UserInfoDetails while MDC setting  => "+userInfoDetails.getUsername());
		MDC.put("logContextFolder", apiCallReceivedOn+"/"+localUserDetails.getUserContext()+"/"+localUserDetails.getUsername());		
	}
}// End of JwtAuthFilter
