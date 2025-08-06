package com.example.security;

import com.example.model.CustomUserDetails;
import com.example.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractEmail(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userOpt = userService.getUserByEmail(email);

            if (userOpt.isPresent() && jwtUtil.validateToken(token)) {
                var user = userOpt.get();
                var customUserDetails = new CustomUserDetails(user);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails, null, customUserDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("JWT Token: " + token);
        System.out.println("Email from Token: " + email);
        System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/auth/signup") || path.equals("/api/auth/login") || path.equals("/api/auth/refresh");
    }
}
