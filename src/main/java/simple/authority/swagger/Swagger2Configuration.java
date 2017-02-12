package simple.authority.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import simple.authority.swagger.doc_hidden.AccessHiddenManager;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public AccessHiddenManager getHiddenBuilder() {
        return new AccessHiddenManager();
    }

    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("simple.authority"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful APIs Test Frame")
                .description("rest_api@winchannel.net")
                .termsOfServiceUrl("http://restful.winchannel.net")
                .contact("wangxiaoxi@winchannel.net")
                .version("2.3")
                .build();
    }
}
