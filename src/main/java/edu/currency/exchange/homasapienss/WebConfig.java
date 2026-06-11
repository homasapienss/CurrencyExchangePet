package edu.currency.exchange.homasapienss;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные методы
                .allowedHeaders("*") // Разрешенные заголовки
                .allowCredentials(true) // Разрешение на отправку куки и авторизационных данных
                .maxAge(3600); // Время кэширования pre-flight запроса браузером (в секундах)
    }
}
