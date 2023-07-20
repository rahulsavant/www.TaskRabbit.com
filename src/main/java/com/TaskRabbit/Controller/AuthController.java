package com.TaskRabbit.Controller;

import com.TaskRabbit.Entity.Role;
import com.TaskRabbit.Entity.User;
import com.TaskRabbit.Payload.JWTAuthResponse;
import com.TaskRabbit.Payload.LoginDto;
import com.TaskRabbit.Payload.SignUpDto;
import com.TaskRabbit.Repository.RoleRepository;
import com.TaskRabbit.Repository.UserRepository;
import com.TaskRabbit.Security.JwtTokenProvider;
import com.TaskRabbit.Service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    //http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
// add check for username exists in a DB
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
// add check for email exists in DB
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
// create user object
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPhoneNumber(signUpDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setZipCode(signUpDto.getZipCode());
        Role roles = roleRepository.findByName("ROLE_ADMIN").orElseThrow(
                () -> new RuntimeException("Default role not found"));
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        // Send email to the user
        sendSignUpConfirmationEmail(user.getEmail());
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    private void sendSignUpConfirmationEmail(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Welcome to TaskRabbit!");
        message.setText("Thank you for signing up with TaskRabbit. We are glad to have you on board!");

        emailSender.send(message);
    }

    //http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    //http://localhost:8080/api/auth/forgot-password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        passwordResetService.generatePasswordResetToken(email);
        return new ResponseEntity<>("Password reset link sent to your email.", HttpStatus.OK);
    }

    //http://localhost:8080/api/auth/reset-password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token,
                                                @RequestParam("newPassword") String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return new ResponseEntity<>("Password reset successful.", HttpStatus.OK);
    }

    // http://localhost:8080/api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Invalidate the authentication and clear the security context
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("Logout successful.");
    }
}


