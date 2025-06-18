package com.example.myweightpal.security;

import com.example.myweightpal.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

// Czym jest komponent w springu

/**
 *  If user is not authenticated throws 401 error
 * */
@Component
public class JwtAuthenticationFilter implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("""
            {
                "error": "Unauthorized",
                "message": "Access token is missing or invalid",
                "status": 401
            }
            """);
    }
}
