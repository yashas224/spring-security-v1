package com.example.spring.springsecurityjwt.config.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.spring.springsecurityjwt.service.UserService;
import com.example.spring.springsecurityjwt.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String autharizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (autharizationHeader != null && autharizationHeader.contains("Bearer ")) {
			String token = autharizationHeader.split(" ")[1];
			String userName = jwtUtil.getUsernameFromToken(token);
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetail = userService.loadUserByUsername(userName);
				if (jwtUtil.validateToken(token, userDetail)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetail, null, userDetail.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			}
		}
		filterChain.doFilter(request, response);
	}

}
