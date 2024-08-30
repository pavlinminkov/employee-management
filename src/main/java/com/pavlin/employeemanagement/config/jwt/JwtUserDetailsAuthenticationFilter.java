package com.pavlin.employeemanagement.config.jwt;

import com.pavlin.employeemanagement.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsAuthenticationFilter extends AbstractJwtAuthenticationFilter {

  private final UserDetailsService userDetailsService;

  public JwtUserDetailsAuthenticationFilter(JwtService jwtService,
      UserDetailsService userDetailsService) {
    super(jwtService);
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected Optional<AbstractAuthenticationToken> getAuthenticationToken(
      HttpServletRequest request,
      String username,
      String jwt) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (userDetails == null || !jwtService.isTokenUserValid(jwt, userDetails)) {
      return Optional.empty();
    }

    return Optional.of(
        new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        )
    );
  }
}