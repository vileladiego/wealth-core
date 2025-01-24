package br.com.wealth.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Permitir todas as rotas
//                        .allowedOrigins("*") // Substitua pelo domínio da aplicação
//                        .allowedMethods("*") // Métodos permitidos
//                        .allowedHeaders("*") // Cabeçalhos permitidos
//                        .allowCredentials(true); // Permitir cookies se necessário
//            }
//        };
//    }
}
