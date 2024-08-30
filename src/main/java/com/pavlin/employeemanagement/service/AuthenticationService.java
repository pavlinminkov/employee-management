package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.AuthenticationRequest;
import com.pavlin.employeemanagement.dto.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
