package com.pavlin.employeemanagement.config.jwt;

import com.pavlin.employeemanagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AbstractJwtAuthenticationFilter extends OncePerRequestFilter {

  protected final JwtService jwtService;

  protected AbstractJwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    processToken(request);
    filterChain.doFilter(request, response);
  }

  protected void processToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    String jwt = authHeader.substring(7);
    String username = jwtService.extractUsername(jwt);

    if (username == null
        || jwtService.isTokenExpired(jwt)
        || SecurityContextHolder.getContext().getAuthentication() != null) {
      return;
    }

    Optional<AbstractAuthenticationToken> token = getAuthenticationToken(request, username, jwt);
    token.ifPresent(autoToken -> {
      autoToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(autoToken);
    });
  }

  protected abstract Optional<AbstractAuthenticationToken> getAuthenticationToken(
      HttpServletRequest request, String username, String jwt);
}