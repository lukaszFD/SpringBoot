import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static <T> List<T> readXlsxFile(File file, Class<T> clazz) throws IOException {
        List<T> result = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                List<String> headers = readRow(headerRow);

                while (rowIterator.hasNext()) {
                    Row dataRow = rowIterator.next();
                    T obj = mapRowToClass(dataRow, headers, clazz);
                    result.add(obj);
                }
            }
        }

        return result;
    }

    private static <T> T mapRowToClass(Row row, List<String> headers, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.newInstance();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                String header = headers.get(columnIndex);

                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class) &&
                            field.getAnnotation(Column.class).name().equals(header)) {
                        field.setAccessible(true);

                        if (field.getType().equals(String.class)) {
                            field.set(obj, cell.getStringCellValue());
                        } else if (field.getType().equals(int.class)) {
                            field.set(obj, (int) cell.getNumericCellValue());
                        } else if (field.getType().equals(double.class)) {
                            field.set(obj, cell.getNumericCellValue());
                        } else if (field.getType().equals(Date.class)) {
                            field.set(obj, cell.getDateCellValue());
                        }
                        // Dodaj obsługę innych typów pól według potrzeb

                        field.setAccessible(false);
                        break;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Błąd podczas mapowania wiersza na obiekt", e);
        }
        return obj;
    }

    private static List<String> readRow(Row row) {
        List<String> headers = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            headers.add(cell.getStringCellValue());
        }

        return headers;
    }
}
