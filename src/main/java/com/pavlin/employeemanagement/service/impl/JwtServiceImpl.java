package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private final JwtParser jwtParser;
  private final JwtBuilder jwtBuilder;

  public JwtServiceImpl(JwtParser jwtParser, JwtBuilder jwtBuilder) {
    this.jwtParser = jwtParser;
    this.jwtBuilder = jwtBuilder;
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Objects.requireNonNull(token);
    Objects.requireNonNull(claimsResolver);

    final Claims claims = extractClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    Objects.requireNonNull(userDetails);

    return generateToken(userDetails, new HashMap<>());
  }

  @Override
  public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
    Objects.requireNonNull(userDetails);
    Objects.requireNonNull(claims);

    return jwtBuilder
        .claims(claims)
        .subject(userDetails.getUsername())
        .compact();
  }

  @Override
  public boolean isTokenUserValid(String token, UserDetails userDetails) {
    Objects.requireNonNull(token);
    Objects.requireNonNull(userDetails);

    String username = extractUsername(token);
    return username.equals(userDetails.getUsername());
  }

  @Override
  public boolean isTokenExpired(String token) {
    Objects.requireNonNull(token);

    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractClaimsFromToken(String token) {
    return jwtParser
        .parseSignedClaims(token)
        .getPayload();
  }
}
