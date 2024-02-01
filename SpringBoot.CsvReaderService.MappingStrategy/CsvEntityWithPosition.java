import com.opencsv.bean.CsvBindByPosition;

public class CsvEntityWithPosition {

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvBindByPosition(position = 1)
    private String firstName;

    @CsvBindByPosition(position = 2)
    private String lastName;

    // Getters, setters, konstruktory, inne metody
}
