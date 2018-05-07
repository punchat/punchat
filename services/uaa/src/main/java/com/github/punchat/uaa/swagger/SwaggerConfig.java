package com.github.punchat.uaa.swagger;

import com.github.punchat.uaa.UaaApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan(basePackageClasses = UaaApplication.class)
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
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext))
                .securitySchemes(Collections.singletonList(securityScheme));
    }
}
