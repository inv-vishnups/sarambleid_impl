package com.inv.scrambleid.service.impl;

import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.forms.LoginRequest;
import com.inv.scrambleid.forms.RegisterRequest;
import com.inv.scrambleid.repository.UserRepository;
import com.inv.scrambleid.security.JwtUtil;
import com.inv.scrambleid.service.AuthService;
import com.inv.scrambleid.service.ScrambleUserService;
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



        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
