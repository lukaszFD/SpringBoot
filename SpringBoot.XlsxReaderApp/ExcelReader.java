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

                while (rowIterator.hasNext()) {
                    Row dataRow = rowIterator.next();
                    T obj = mapRowToClass(dataRow, clazz);
                    result.add(obj);
                }
            }
        }

        return result;
    }

    private static <T> T mapRowToClass(Row row, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.newInstance();
            Iterator<Cell> cellIterator = row.cellIterator();
			Field[] fields = clazz.getDeclaredFields();
			int entityFieldIndex = 1;

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
				Field[] field = fields[entityFieldIndex];
				field.setAccessible(true);
				field.setAccessible(true);
				
				field.set(obj, cell.toString());
				field.setAccessible(false);
				entityFieldIndex++;

            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}