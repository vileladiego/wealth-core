package br.com.wealth.core.dto;

public record UserResponseDTO(
        Long userId,
        String username,
        String roles
) {
}
