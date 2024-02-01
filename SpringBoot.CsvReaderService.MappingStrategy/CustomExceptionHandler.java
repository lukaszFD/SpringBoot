import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.ICSVParserExceptionHandler;

import java.lang.reflect.Field;

public class CustomExceptionHandler<T> implements ICSVParserExceptionHandler {
    private final Class<T> clazz;

    public CustomExceptionHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void handleError(CsvBadConverterException exception) throws CsvException {
        String header = exception.getLine();
        String[] headerArray = header.split(exception.getCapturedLineDelimiter());

        int expectedColumnCount = calculateExpectedColumnCount(clazz);

        if (headerArray.length > expectedColumnCount) {
            // Tutaj możesz obsłużyć błąd, np. zalogować go
            System.err.println("Error: Too many columns in CSV file");
        } else if (headerArray.length < expectedColumnCount) {
            // Tutaj możesz obsłużyć błąd, np. zalogować go
            System.err.println("Error: Too few columns in CSV file");
        }
    }

    private int calculateExpectedColumnCount(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int count = 0;

        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvBindByName.class) || field.isAnnotationPresent(CsvBindByPosition.class)) {
                count++;
            }
        }

        return count;
    }
}
