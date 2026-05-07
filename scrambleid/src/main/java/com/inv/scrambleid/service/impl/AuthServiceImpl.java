package com.inv.scrambleid.service.impl;

import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.forms.LoginRequest;
import com.inv.scrambleid.forms.RegisterRequest;
import com.inv.scrambleid.repository.UserRepository;
import com.inv.scrambleid.security.JwtUtil;
import com.inv.scrambleid.security.ScrambleIdTokenVerifier;
import com.inv.scrambleid.service.AuthService;
import com.inv.scrambleid.service.ScrambleAuthService;
import com.inv.scrambleid.service.ScrambleUserService;
import com.inv.scrambleid.view.LoginResponse;
import com.inv.scrambleid.view.TokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final ScrambleUserService scrambleUserService;

    private final ScrambleAuthService scrambleAuthService;

    private final ScrambleIdTokenVerifier scrambleIdTokenVerifier;

    @Override
    public String register(RegisterRequest request) {

        // Check if user already exists
        userRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already registered");
                });

        String scrambleUserId = scrambleUserService.createUser(request);

        User user = User.builder()
                .userName(request.firstName()+" "+request.lastName())
                .givenName(request.firstName())
                .familyName(request.lastName())
                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
                .scrambleUserId(scrambleUserId)
                .role("USER")
                .active(true)
                .desktopAppEnabled(true)
                .mobileAppEnabled(true)
                .sendActivation(true)
                .sendDesktopActivation(true)
                .build();

        userRepository.save(user);

        return "Successfully saved";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        TokenResponse tokenResponse = scrambleAuthService
                .getAccessTokenByAuthorizationCode(request.code())
                .block();

        if (tokenResponse == null) {
            throw new RuntimeException("Token exchange returned no response");
        }

        Claims claims = scrambleIdTokenVerifier.parseAndVerify(tokenResponse.getIdToken());

        String email = claims.get("email", String.class);
        if (email == null || email.isBlank()) {
            email = claims.getSubject();
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Email --- "+email);

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(user.getUserName(), user.getEmail(), token);
    }
}
