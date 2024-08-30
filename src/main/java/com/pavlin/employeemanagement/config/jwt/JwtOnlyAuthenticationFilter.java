package com.pavlin.employeemanagement.config.jwt;

import com.pavlin.employeemanagement.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Primary
@Component
public class JwtOnlyAuthenticationFilter extends AbstractJwtAuthenticationFilter {

  public JwtOnlyAuthenticationFilter(JwtService jwtService) {
    super(jwtService);
  }

  @Override
  protected Optional<AbstractAuthenticationToken> getAuthenticationToken(
      HttpServletRequest request,
      String username,
      String jwt) {
    List<SimpleGrantedAuthority> authorities = extractAuthorities(jwt);

    return Optional.of(new UsernamePasswordAuthenticationToken(
            username,
            null,
            authorities
        )
    );
  }

  private List<SimpleGrantedAuthority> extractAuthorities(String jwt) {
    List<?> authoritiesObj = jwtService.extractClaim(
        jwt,
        claims -> claims.get("authorities", List.class)
    );

    return authoritiesObj.stream()
        .filter(String.class::isInstance)
        .map(String.class::cast)
        .map(SimpleGrantedAuthority::new)
        .toList();
  }
}