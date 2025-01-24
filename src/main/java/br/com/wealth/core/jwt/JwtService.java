package br.com.wealth.core.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Authentication autentication) {
        Instant now = Instant.now();
        String scopes = autentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("wealth-core-security")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .subject(autentication.getName())
                .claim("scope", scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}
