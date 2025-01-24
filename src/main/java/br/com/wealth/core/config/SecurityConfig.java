package br.com.wealth.core.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/logout").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(
                        conf -> conf.jwt(Customizer.withDefaults()))
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true));

        return http.build();
//        http.authorizeHttpRequests(authorize -> authorize //
//                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
//
//                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/logout").permitAll()
//                        //.requestMatchers(HttpMethod.POST, "/bff/**").permitAll()
//                        //.requestMatchers(new AntPathRequestMatcher("/api/v1/**")).permitAll()  // Permite todos os métodos para a rota /api/v1/**
//                        .anyRequest().authenticated())
//
//                .csrf(csrf -> csrf.disable())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(key).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        var jwk = new RSAKey.Builder(key).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
