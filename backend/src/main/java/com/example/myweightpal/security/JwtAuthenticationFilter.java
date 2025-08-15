package com.example.myweightpal.security;

import com.example.myweightpal.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwt;
    private final UserService users;

    public JwtAuthenticationFilter(JwtUtil jwt, UserService users) {
        this.jwt = jwt;
        this.users = users;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            log.debug("Authorization Bearer present");

            if (jwt.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String userId = jwt.userId(token);
                log.debug("Token valid, userId from claims={}", userId);

                users.findById(userId).ifPresentOrElse(u -> {
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
                    var authentication = new UsernamePasswordAuthenticationToken(u, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("SecurityContext set for user {} with {}", u.getEmail(), authorities);
                }, () -> log.warn("No user found for id {} from token", userId));
            } else {
                log.debug("Token invalid OR auth already present");
            }
        }
        chain.doFilter(req, res);
    }
}
