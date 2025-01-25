package br.com.wealth.core.services;


import br.com.wealth.core.entities.User;
import br.com.wealth.core.entities.PasswordResetToken;
import br.com.wealth.core.repositories.PasswordResetTokenRepository;
import br.com.wealth.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createPasswordResetTokenForUser(String email) {
        // Verifica se o email existe
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Remove tokens antigos
        tokenRepository.findByEmail(email).ifPresent(tokenRepository::delete);

        // Cria novo token
        String token = generateToken();
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setEmail(email);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(myToken);

        // Envia email
        sendPasswordResetEmail(email, token);
    }

    public boolean validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (passToken.isUsed() || passToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        return true;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (!validatePasswordResetToken(token)) {
            throw new RuntimeException("Token expirado ou já utilizado");
        }

        User user = userRepository.findByEmail(passToken.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passToken.setUsed(true);
        tokenRepository.save(passToken);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset de Senha");
        message.setText("Para resetar sua senha, clique no link:\n" +
                "http://localhost:8080/reset-password?token=" + token);
        mailSender.send(message);
    }
}
