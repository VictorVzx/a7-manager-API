package br.com.victorvzx.a7manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera todos os caminhos da API
                .allowedOrigins("*") // Aceita de QUALQUER origem (todo lugar)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Todos os verbos HTTP
                .allowedHeaders("*") // Aceita todos os Headers (Content-Type, Authorization, etc)
                .exposedHeaders("*");
    }
}