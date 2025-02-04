package ynov.architecture.ds.apigateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi bookApi() {
        return GroupedOpenApi.builder()
                .group("book-service")
                .pathsToMatch("/book-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/user-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi borrowingApi() {
        return GroupedOpenApi.builder()
                .group("borrowing-service")
                .pathsToMatch("/borrowing-service/**")
                .build();
    }
}
