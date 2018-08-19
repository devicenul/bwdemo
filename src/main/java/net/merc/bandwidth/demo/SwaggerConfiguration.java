package net.merc.bandwidth.demo;


import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration {
        @Bean
        public Docket documentation() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(regex("/bandwidth/.*"))
                    .build()
                    .pathMapping("/")
                    .apiInfo(metadata());
        }


    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                //.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl("") // null should work, but that's a bug in springfox 2.8.0. See https://github.com/springfox/springfox/issues/2201
                .build();
    }


    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Bandwidth: Tiny Demo")
                .description("An API to try out some simple Bandwidth APIs. Last Updated:" + new Date())
                .version("1.0")
                .contact(new Contact("Rich Jefferson", "github.com/devicenul/bwdemo", "rrjefferson@gmail.com"))
                .build();
    }
}
