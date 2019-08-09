package com.antonromanov.arnote.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {
		Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(servletRequest, servletResponse);
		} catch (ExpiredJwtException eje)  {
			LOGGER.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			LOGGER.debug("Exception " + eje.getMessage(), eje);
		}
	}
}
