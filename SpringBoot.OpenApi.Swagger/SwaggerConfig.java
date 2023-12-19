@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("twoja.sciezka.do.kontrolerow"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("v1")
                .produces(Collections.singleton("application/json"))
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                        new ResponseMessageBuilder()
                                .code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build(),
                        new ResponseMessageBuilder()
                                .code(403)
                                .message("Forbidden!")
                                .build()));
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Nazwa Twojej aplikacji")
                .description("Opis Twojej aplikacji")
                .version("1.0.0")
                .contact(new Contact("Twoje imię i nazwisko", "link-do-strony", "adres-email"))
                .build();
    }
}
