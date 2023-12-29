package com.hotel.service.security;

import com.hotel.model.dto.ClaimsDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// This class helps us to validate the generated jwt token

@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            if (!token.isEmpty()) {

                try {
                    ClaimsDto claimsDto = jwtService.parseToken(token);
                    PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(claimsDto, null, Collections.singleton(new SimpleGrantedAuthority(claimsDto.getRole())));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (JwtException e) {
                    logger.warn("Invalid token");
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
