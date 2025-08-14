package com.testtask.authapi.service;

import com.testtask.authapi.dto.LoginRequest;
import com.testtask.authapi.dto.RegistrationRequest;
import com.testtask.authapi.dto.TokenResponse;
import com.testtask.authapi.entity.UserEntity;
import com.testtask.authapi.exception.ConflictException;
import com.testtask.authapi.exception.UnauthorizedException;
import com.testtask.authapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequest request) {
        String normalizedEmail = normalizeEmail(request.email());
        requireUniqueEmail(normalizedEmail);

        UserEntity user = UserEntity.builder()
                .email(normalizedEmail)
                .passwordHash(passwordEncoder.encode(request.password()))
                .createdAt(Instant.now())
                .build();

        userService.save(user);
    }

    public TokenResponse login(LoginRequest request) {
        String normalizedEmail = request.email();
        UserEntity user = userService.getByEmailIgnoreCase(normalizedEmail);

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return new TokenResponse(jwtService.generateToken(user.getId()));
    }

    private void requireUniqueEmail(String email) {
        if (userService.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("User with email: %s already exists".formatted(email));
        }
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
