package com.nseit.shareholder1.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.keycloak.representations.idm.authorization.Permission;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;

@Slf4j
@Component
public class CustomSecurityFilter extends GenericFilterBean {

	ArrayList<RequestMatcher> skipMatchers = new ArrayList<RequestMatcher>(Arrays.asList(
			new AntPathRequestMatcher("/shareholder/**"),
			new AntPathRequestMatcher("/brokerregistration/**"),
			new AntPathRequestMatcher("/nseuser/nseLogin/**"),
			new AntPathRequestMatcher("/duo/**"),
			new AntPathRequestMatcher("/Session/logout/**"),
			// new AntPathRequestMatcher("/benposeDataMaster/**"),
			new AntPathRequestMatcher("/reports/**"),
			new AntPathRequestMatcher("/"),
			new AntPathRequestMatcher("/**.html"),
			new AntPathRequestMatcher("/**.js"),
			new AntPathRequestMatcher("/**.css"),
			new AntPathRequestMatcher("/**.woff"),
			new AntPathRequestMatcher("/**.woff2"),
			new AntPathRequestMatcher("/**.ico"),
			new AntPathRequestMatcher("/assets/**"),
			new AntPathRequestMatcher("/#/**")));

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	@Autowired
	ResponseUtil responseUtil;

	@Autowired
	JwtUtil jwt;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			AuthorizationRequest ar = new AuthorizationRequest();
			List<String> resourceList = new ArrayList<String>();
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			// log.debug("customFilter---------------------->" +
			// customFilterUrl.matches(httpServletRequest));
			// log.debug("customFilter1---------------------->"+customFilterUrl1.matches(httpServletRequest));
			// if (customFilterUrl.matches(httpServletRequest)||
			// customFilterUrl1.matches(httpServletRequest)) {
			if (skipMatchers.stream().anyMatch((t) -> t.matches(httpServletRequest))) {
				log.trace("Auth Filter bypass---------------------->");
				// it should not be invoked for
				// "/shareholder/authentication/register","/shareholder/authentication/login",....
				// continue to next filter/controller
				chain.doFilter(request, response);
			} else {
				// "Filter Logic for keycloak
				// findResourceByURI(httpServletRequest.getRequestUri().toString())
				AuthzClient ac = keycloakClientAuthExample.getAuthzClient();
				if (request instanceof HttpServletRequest) {
					String url = ((HttpServletRequest) request).getRequestURL().toString();
					String uri = ((HttpServletRequest) request).getRequestURI();
					String queryString = ((HttpServletRequest) request).getQueryString();
					log.debug("URL -------------" + url);

					// String last = url.substring(url.lastIndexOf("/"));
					log.trace("URI -------------" + uri);

					// log.debug("uri In KEYCLOAK Filter--------------------------->" +
					// last);
					List<ResourceRepresentation> protection = ac.protection().resource().findByMatchingUri(uri);
					for (ResourceRepresentation perm : protection) {
						String resourceName = perm.getName();

						ar.addPermission(resourceName);
						resourceList.add(resourceName);
						log.debug("resourceName----------->" + resourceName);
					}
					// AuthorizationResponse authresponse =
					// ac.authorization(authorization.substring(7)).authorize(ar);
					log.trace("User Token----------------->"
							+ ((HttpServletRequest) request).getHeader("Authorization").substring(7));
					String token = ((HttpServletRequest) request).getHeader("Authorization").substring(7);
					log.debug("token in security----------" + token);
					jwt.setToken(token);
					AuthorizationResponse authresponse = ac
							.authorization(((HttpServletRequest) request).getHeader("Authorization").substring(7))
							.authorize(ar);
					TokenIntrospectionResponse rpt = ac.protection()
							.introspectRequestingPartyToken(authresponse.getToken());
					if (rpt.isActive()) {
						log.trace("rpt----------->" + rpt);
						List<Permission> permissions = rpt.getPermissions();
						boolean permissionsFound = false;
						log.trace("permissions------->" + permissions);
						for (Permission p : permissions) {

							if (resourceList.contains(p.getResourceName())) {
								// return false;

								permissionsFound = true;

							}

							log.debug("False line 71------------------->" + p.getResourceName());
						}
						if (permissionsFound == true) {

							chain.doFilter(request, response);
							log.debug("TRUE line 74------------------->");
						} else {

							log.debug("Access Denied FILTER FORBIDDEN------------------->");
							((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
						}

					} else {

						log.debug("Access Denied FILTER UNAUTH------------------->");
						// error throw
						// responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED , null,
						// null);
						((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

					}
				}

			}
		} catch (RuntimeException hr) {

			log.debug("ACCESS DENIED Exception-------" + hr.getMessage());

			if (hr.getMessage().contains("401")) {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

			} else if (hr.getMessage().contains("403")) {
				((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());

			} else {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

			}

			// ResponseEntity<?> responseEntity=responseUtil.getAuthResponse("UNAUTHORIZED",
			// HttpStatus.UNAUTHORIZED , null, null);
			// sendError(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");

		}

	}
}