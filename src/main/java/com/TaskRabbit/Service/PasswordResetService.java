package com.TaskRabbit.Service;

import com.TaskRabbit.Entity.PasswordResetToken;
import com.TaskRabbit.Entity.User;
import com.TaskRabbit.Repository.PasswordResetTokenRepository;
import com.TaskRabbit.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with the given email"));

        // Delete any existing tokens for the user
        passwordResetTokenRepository.deleteByUserId(user.getId());

        // Generate a new password reset token and save it
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(new Date(System.currentTimeMillis() + (60 * 60 * 1000))); // Token valid for 1 hour
        passwordResetTokenRepository.save(token);

        // Send the password reset link to the user's email
        sendPasswordResetEmail(user.getEmail(), token.getToken());
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Invalid or expired token");
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the used token
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    private void sendPasswordResetEmail(String userEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Password Reset - TaskRabbit");
        message.setText("Click on the link below to reset your password:\n"
                + "http://yourdomain.com/reset-password?token=" + token);

        emailSender.send(message);
    }
}

