import com.opencsv.bean.CsvBindByName;

public class CsvEntityWithName {

    @CsvBindByName(column = "ID")
    private Long id;

    @CsvBindByName(column = "FirstName")
    private String firstName;

    @CsvBindByName(column = "LastName")
    private String lastName;

    // Getters, setters, konstruktory, inne metody
}
