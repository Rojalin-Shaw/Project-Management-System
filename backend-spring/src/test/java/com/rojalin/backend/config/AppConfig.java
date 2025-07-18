package com.rojalin.backend.config;

import java.util.Arrays;
import java.util.Collections;

import org.hibernate.boot.cfgxml.spi.CfgXmlAccessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // ✅ Add this method to fix your issue
    private CorsConfigurationSource corsConfigurationSource() {
    	return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				// TODO Auto-generated method stub
				CorsConfiguration cfg = new CorsConfiguration();
			       cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000",
			    		   "http://localhost:5173",
			    		   "http://localhost:4200"));
			       cfg.setAllowedMethods(Collections.singletonList("*"));
			       cfg.setAllowCredentials(true);
			       cfg.setAllowedHeaders(Collections.singletonList("*"));
			       cfg.setExposedHeaders(Arrays.asList("Authorization"));
			       cfg.setMaxAge(3600L);
				return cfg;
			}
		};
      
    }
    @Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
