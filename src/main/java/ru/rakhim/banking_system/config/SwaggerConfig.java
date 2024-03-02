package ru.rakhim.banking_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("add-user-id-header")
//                .addOperationCustomizer((operation, handlerMethod) -> {
//                    String path = handlerMethod.getMethod().getName();
//
//                    // Проверяем путь операции и исключаем методы, которые соответствуют пути "/register"
//                    if (!path.equals("register") && !path.equals("login")) {
//                        operation.addParametersItem(
//                                new HeaderParameter()
//                                        .name("Authorization")
//                                        .description("Access Token")
//                                        .required(true)
//                                        .schema(new StringSchema().pattern("^Bearer\\s.+"))// Устанавливаем шаблон для токена
//                        );
//                    }
//                    return operation;
//                })
//                .build();
//    }

}
