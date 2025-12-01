package com.wealth.tracker.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wealth.tracker.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (request.getMethod().equalsIgnoreCase("OPTIONS") || path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
    }
        String header = request.getHeader("Authorization");

if (header != null && header.startsWith("Bearer ")) {
    String token = header.substring(7).trim();

    try {
        String pan = jwtUtil.validateAndGetSubject(token);
        System.out.println("TOKEN RECEIVED: " + token);


        var auth = new UsernamePasswordAuthenticationToken(
                pan, null, java.util.Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (Exception e) {
        System.out.println("JWT ERROR : " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }
}
    
        filterChain.doFilter(request, response);
    }
}
