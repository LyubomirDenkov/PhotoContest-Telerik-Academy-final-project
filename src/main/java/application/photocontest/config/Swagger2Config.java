package application.photocontest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;


@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(newArrayList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Username")
                        .modelRef(new ModelRef("String"))
                        .parameterType("header")
                        .required(false)
                        .build()))
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Iphoto Documentation")
                .description(description())
                .version("1.0")
                .build();
    }

    private String description(){
        return "iPhoto is a platform powered by an amazing community that has uploaded hundreds of thousands\n" +
                "of their own photos to fuel creativity around the world. You can sign up for free.\n" +
                "Either way, you've got access to over a million photos under the iPhoto\n" +
                "license - which makes them free to do whatever you like with them. \n" +
                "See also at: https://gitlab.com/Nikolayy9/photo-contest";
    }

}
