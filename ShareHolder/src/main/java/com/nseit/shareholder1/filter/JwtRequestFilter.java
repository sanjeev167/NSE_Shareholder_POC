//package com.nseit.shareholder1.filter;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.nseit.shareholder1.model.UserMaster;
//import com.nseit.shareholder1.service.ShareHolderService;
//import com.nseit.shareholder1.util.JwtUtil;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private ShareHolderService userLoginService1;
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//
//		final String authorizationHeader = request.getHeader("Authorization");
//		String acceptHeader = request.getHeader("Accept");
//		String contentTypeHeader = request.getHeader("Content-Type");
//		logger.info("request url : " + request.getRequestURI());
//		logger.info("method Type : " + request.getMethod());
//		logger.info("authorizationHeader : " + authorizationHeader);
//		logger.info("acceptHeader : " + acceptHeader);
//		logger.info("contentTypeHeader : " + contentTypeHeader);
//		String username = null;
//		String jwt = null;
//
////        if(acceptHeader ==null && !"application/json".equals(acceptHeader)) {
////        	acceptHeader="application/json";
////        }
////        
////        if(contentTypeHeader ==null && !"application/json".equals(contentTypeHeader)) {
////        	contentTypeHeader="application/json";
////        }
////        
////        logger.info("after acceptHeader : "+ acceptHeader);
////        logger.info("after contentTypeHeader : "+ contentTypeHeader);
//
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			jwt = authorizationHeader.substring(7);
//			logger.info("jwt : " + jwt);
//			username = jwtUtil.extractUsername(jwt);
//			logger.info("username : " + username);
//		}
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			UserMaster userDetails = userLoginService1.loadUserByUserName(username);
//			Long userId = userDetails.getId();
//			boolean isTokenExistinDb = userLoginService1.isUserTokenExist(userId, jwt) > 0 ? true : false;
//			// boolean isTokenExistinDb= userDetails!=null?true:false;
//			logger.info("isTokenExistinDb----->" + isTokenExistinDb);
//			// boolean isTokenExistinDb=true;
//			if (jwtUtil.validateToken(jwt, isTokenExistinDb)) {
//				logger.info("Inside if validate token-------------->  ");
//
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, null);
//				usernamePasswordAuthenticationToken
//						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
////            else {
////            	throw new Exception("NOT AUTHORISED");
////            }
//		}
//		chain.doFilter(request, response);
//	}
//
//}
