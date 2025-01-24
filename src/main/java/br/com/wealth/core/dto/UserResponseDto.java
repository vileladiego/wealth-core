package br.com.wealth.core.dto;

public record UserResponseDto(
        Long userId,
        String username,
        String roles
) {
}
