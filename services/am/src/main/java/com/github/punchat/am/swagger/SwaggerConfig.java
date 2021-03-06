package com.github.punchat.am.swagger;

import com.github.punchat.am.AccountManagementApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
@ConditionalOnProperty("swagger.enabled")
@ComponentScan(basePackageClasses = AccountManagementApplication.class)
public class SwaggerConfig {
    private final SecurityContext securityContext;
    private final SecurityScheme securityScheme;

    public SwaggerConfig(SecurityContext securityContext, SecurityScheme securityScheme) {
        this.securityContext = securityContext;
        this.securityScheme = securityScheme;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext))
                .securitySchemes(Collections.singletonList(securityScheme));
    }
}
