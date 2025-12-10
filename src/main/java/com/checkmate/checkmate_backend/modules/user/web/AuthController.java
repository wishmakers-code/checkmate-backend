package com.checkmate.checkmate_backend.modules.user.web;

import com.checkmate.checkmate_backend.modules.user.domain.User;
import com.checkmate.checkmate_backend.modules.user.dto.AuthResponse;
import com.checkmate.checkmate_backend.modules.user.dto.LoginRequest;
import com.checkmate.checkmate_backend.modules.user.dto.RegisterRequest;
import com.checkmate.checkmate_backend.modules.user.service.UserService;
import com.checkmate.checkmate_backend.security.JwtProperties;
import com.checkmate.checkmate_backend.security.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          JwtProperties jwtProperties) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request,
                                 HttpServletResponse response) {

        if (userService.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // matches your UserService signature: createUser(email, hashedPassword, fullName)
        User saved = userService.createUser(
                request.getEmail(),
                hashedPassword,
                request.getFullName()
        );

        String token = jwtService.generateToken(saved);
        attachAuthCookie(response, token);

        return new AuthResponse(saved.getId(), saved.getEmail(), saved.getFullName());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletResponse response) {

        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        attachAuthCookie(response, token);

        return new AuthResponse(user.getId(), user.getEmail(), user.getFullName());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(jwtProperties.getCookieName(), "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set true behind HTTPS
        response.addCookie(cookie);
    }

    @GetMapping("/me")
    public AuthResponse me(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        return new AuthResponse(user.getId(), user.getEmail(), user.getFullName());
    }

    private void attachAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(jwtProperties.getCookieName(), token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set true in HTTPS
        cookie.setMaxAge((int) jwtProperties.getExpirationSeconds());
        response.addCookie(cookie);
    }
}
