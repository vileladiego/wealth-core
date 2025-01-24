package br.com.wealth.core.controllers;


import br.com.wealth.core.dto.MessageResponseDto;
import br.com.wealth.core.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            passwordResetService.createPasswordResetTokenForUser(email);
            return ResponseEntity.ok().body(new MessageResponseDto("Email de recuperação enviado"));
        } catch (UsernameNotFoundException e) {
            // Por segurança, não revelamos se o email existe ou não
            return ResponseEntity.ok().body(new MessageResponseDto("Se o email existir, você receberá as instruções"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok().body(new MessageResponseDto("Senha alterada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validatePasswordResetToken(token);
        if (isValid) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}