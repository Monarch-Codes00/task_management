package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.*;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.UserRepository;
import com.aptechph.financial_dashboard.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        user.setIsEmailVerified(true);  // Set to true for demo purposes
        user.setIsAccountActive(true);

        userRepository.save(user);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        userDto.setIsAccountActive(user.getIsAccountActive());

        return new AuthResponse(jwt, refreshToken, userDto, jwtUtils.getExpirationTime());
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        User user = (User) authentication.getPrincipal();
        user.setLastLoginAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        userDto.setIsAccountActive(user.getIsAccountActive());

        return new AuthResponse(jwt, refreshToken, userDto, jwtUtils.getExpirationTime());
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtUtils.validateJwtToken(refreshToken)) {
            String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken = jwtUtils.generateTokenFromUsername(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole().name());
            userDto.setIsEmailVerified(user.getIsEmailVerified());
            userDto.setIsAccountActive(user.getIsAccountActive());

            return new AuthResponse(newAccessToken, newRefreshToken, userDto, jwtUtils.getExpirationTime());
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}