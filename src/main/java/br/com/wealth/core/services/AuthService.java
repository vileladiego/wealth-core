package br.com.wealth.core.services;


import br.com.wealth.core.dto.LoginResponseDTO;
import br.com.wealth.core.dto.UserResponseDTO;
import br.com.wealth.core.entities.User;
import br.com.wealth.core.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import br.com.wealth.core.entities.Role;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;

    @Value("${jwt.expiration}")
    private long expiration;

    public LoginResponseDTO authenticate(Authentication authentication) {

        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
//        Long personaId = this.findPersonaId(user.getId(), roles);
        return new LoginResponseDTO(jwtService.generateToken(authentication), expiration,
                new UserResponseDTO(user.getId(), user.getEmail(), roles), 1L);
    }

    private Long findPersonaId(Long id, String roles) {
        Long ret = id;
        return ret;
    }
}