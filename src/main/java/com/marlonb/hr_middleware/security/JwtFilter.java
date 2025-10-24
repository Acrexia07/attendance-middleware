package com.marlonb.hr_middleware.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AdminUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = "Authorization";
        String tokenString = "Bearer ";
        String authHeader =request.getHeader(authorization);
        String token = null;
        String username = null;

        // PROCESS: Executing filtration of JWT
        try {

            // PHASE 1: Verify if the header has bearer token
            //          If it has a token, extract the JWT
            if (authHeader != null && authHeader.startsWith(tokenString)) {
                token =authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }

            // PHASE 2: Verify if the token has username in it
            //          Check if authentication set on SecurityContextHolder is null
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user by username with the customized user detail service
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // PHASE 3: Validate JWT
                if (jwtService.isTokenValid(token, userDetails.getUsername())) {

                    // Authenticate username and password of the validated token
                    var authToken = new UsernamePasswordAuthenticationToken(
                            token, null,userDetails.getAuthorities()
                    );

                    // Pass that authenticated token to the security context holder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}
