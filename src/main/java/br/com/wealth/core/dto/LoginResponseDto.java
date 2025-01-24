package br.com.wealth.core.dto;

public record   LoginResponseDto(
        String accessToken,
        Long expiresIn,
        UserResponseDto user,
        Long personaId) {
}