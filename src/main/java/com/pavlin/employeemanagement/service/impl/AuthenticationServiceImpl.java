package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.dto.AuthenticationRequest;
import com.pavlin.employeemanagement.dto.AuthenticationResponse;
import com.pavlin.employeemanagement.service.JwtService;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements
    com.pavlin.employeemanagement.service.AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtService jwtService;

  public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.username(),
            authenticationRequest.password()
        )
    );
    UserDetails userDetails = userDetailsService.loadUserByUsername(
        authenticationRequest.username());

    List<String> authorities = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    Map<String, Object> claims = Map.of("authorities", authorities);
    return new AuthenticationResponse(
        jwtService.generateToken(userDetails, claims)
    );
  }
}
