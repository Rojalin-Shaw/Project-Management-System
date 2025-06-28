package com.rojalin.backend.config;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.crypto.SecretKey;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt != null) {
			jwt =jwt.substring(7);
			try {
				SecretKey key = KeySetView.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				String emailString =String.valueOf(claims.get("email"));
				String authorities = String.valueOf(claims.get("authorities"));
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null ,auths);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadCredentialsException("invalid token...");
			}
		}
		filterChain.doFilter(request, response);
		
	}

	
}
