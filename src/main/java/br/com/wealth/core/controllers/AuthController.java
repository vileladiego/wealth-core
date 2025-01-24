package br.com.wealth.core.controllers;


import br.com.wealth.core.dto.LoginResponseDto;
import br.com.wealth.core.dto.UserRegistrationDto;
import br.com.wealth.core.entities.User;
import br.com.wealth.core.services.AuthService;
import br.com.wealth.core.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));//todo: fazer mapper para o dto
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(Authentication authentication) {
        return ResponseEntity.ok().body(authService.authenticate(authentication));
    }

}
