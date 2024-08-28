package com.user.management.controller;

import com.user.management.config.BusinessException;
import com.user.management.dto.*;
import com.user.management.service.AuthenticationService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService service;

    @PostMapping("/register")
    public GeneralResponse<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) throws BusinessException {
        return service.saveUser(request);
    }

    @PostMapping("/authenticate")
    public GeneralResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws BadCredentialsException, BusinessException {
        try {
            return new GeneralResponse("00", "Success",  service.authenticate(request));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "01", "User name or password is not correct");
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "02", "Internal Server Error");
        }
    }

    @PostMapping("/refresh-token")
    public GeneralResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException, MalformedJwtException {
        try {
            GeneralResponse<AuthenticationResponse> authenticationResponse = service.refreshToken(request, response);
            if (authenticationResponse.getCode().equals("00")) {
                return authenticationResponse;
            } else {
                throw new BusinessException(HttpStatus.FORBIDDEN, authenticationResponse.getCode(), authenticationResponse.getMessage());
            }
        } catch (MalformedJwtException e) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "01", "Token is not valid");
        } catch(Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "02", "Internal Server Error");
        }
    }
}
