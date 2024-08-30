package com.pavlin.employeemanagement.service;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String extractUsername(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String generateToken(UserDetails userDetails);

  String generateToken(UserDetails userDetails, Map<String, Object> claims);

  boolean isTokenUserValid(String token, UserDetails userDetails);
}
