package com.user.management.service;

import com.user.management.config.BusinessException;
import com.user.management.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    GeneralResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);

    String encodePassword(String password);

    GeneralResponse<CreateUserResponse> saveUser(CreateUserRequest request) throws BusinessException;
}
