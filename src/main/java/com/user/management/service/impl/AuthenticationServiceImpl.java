package com.user.management.service.impl;

import com.user.management.config.BusinessException;
import com.user.management.config.security.JwtService;
import com.user.management.dto.*;
import com.user.management.enums.Role;
import com.user.management.enums.TokenType;
import com.user.management.models.Token;
import com.user.management.models.User;
import com.user.management.repository.TokenRepository;
import com.user.management.repository.UserRepository;
import com.user.management.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public GeneralResponse<CreateUserResponse> saveUser(CreateUserRequest request) throws BusinessException {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        repository.save(user);

        CreateUserResponse responseDTO = CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .build();
        return new GeneralResponse<>("00", "data successfully saved", responseDTO);
    }

    @Override
    public String encodePassword(String password) {
        if (Objects.nonNull(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public GeneralResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        boolean isTokenValid = true;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            isTokenValid = false;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        AuthenticationResponse authToken = new AuthenticationResponse();
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                authToken =  AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                isTokenValid = false;
            }
        } else {
            isTokenValid = false;
        }

        if (isTokenValid) {
            return new GeneralResponse<>("00","Success", authToken);
        } else {
            return new GeneralResponse<>("01", "Username or Password is not correct", null);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
