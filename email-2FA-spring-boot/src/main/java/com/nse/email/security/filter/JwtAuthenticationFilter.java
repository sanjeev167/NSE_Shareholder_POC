package com.nse.email.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nse.email.security.CustomUserDetailService;
import com.nse.email.security.utility.JwtUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailService customUserDetialService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith("Bearer ")) {
                final var token = authorizationHeader.substring(7);
                final var userId = jwtUtils.extractUserId(token);

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = customUserDetialService.loadUserByUsername(userId.toString());

                    if (jwtUtils.validateToken(token, userDetails)) {

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}