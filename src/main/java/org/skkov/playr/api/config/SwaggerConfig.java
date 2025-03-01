package org.skkov.playr.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Swagger (Springdoc OpenAPI).
 */
@Configuration
public class SwaggerConfig {

  /**
   * Определяет базовую информацию о API.
   * @return OpenAPI с настройками.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Playr API")
            .version("1.0")
            .description("Документация API для сервера Playr"));
  }
}
