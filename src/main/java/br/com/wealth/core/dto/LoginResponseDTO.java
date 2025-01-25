package br.com.wealth.core.dto;

public record LoginResponseDTO(
        String accessToken,
        Long expiresIn,
        UserResponseDTO user,
        Long personaId) {
}