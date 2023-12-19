import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Przykładowy obiekt")
public class ExampleObject {

    @ApiModelProperty(notes = "Nazwa obiektu", example = "John Doe")
    private String name;

    @ApiModelProperty(notes = "Wiek obiektu", example = "30")
    private int age;

    // Getters and setters
}
