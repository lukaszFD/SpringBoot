okprivate <T> T mapRowToClass(Row row, Class<T> clazz) {
    try {
        T obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        
        // Przetwarzaj pola klasy
        for (Field field : fields) {
            // Sprawdź, czy pole ma adnotację ExcelColumn
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                field.setAccessible(true);

                // Pobierz numer kolumny z adnotacji
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                int columnIndex = excelColumn.value();

                // Pobierz komórkę na podstawie numeru kolumny
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                // Sprawdź, czy komórka jest pusta
                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    field.set(obj, null);  // Ustaw null dla pustych komórek
                } else {
                    // Zawsze konwertujemy wartość komórki na String
                    field.set(obj, cell.toString());
                }
            }
        }
        
        return obj;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
    }
}


private <T> T mapRowToClass(Row row, Class<T> clazz) {
    try {
        T obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        
        // Przechodzimy po komórkach w wierszu
        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();  // Pobierz numer kolumny

            // Sprawdzamy, czy numer kolumny nie przekracza liczby pól w klasie
            if (columnIndex < fields.length) {
                Field field = fields[columnIndex];
                field.setAccessible(true);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    field.set(obj, null);  // Ustaw null dla pustych komórek
                } else {
                    field.set(obj, cell.toString());  // Wszystkie pola są typu String
                }
            }
        }
        
        return obj;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
    }
}

private <T> T mapRowToClass(Row row, Class<T> clazz) {
    try {
        T obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();  // Pobierz numer kolumny

            if (columnIndex < fields.length) {
                Field field = fields[columnIndex];
                field.setAccessible(true);

                // Sprawdzamy typ komórki i konwertujemy ją na String
                String cellValue;
                switch (cell.getCellType()) {
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = cell.getDateCellValue().toString();  // Konwersja daty na String
                        } else {
                            cellValue = Double.toString(cell.getNumericCellValue());  // Konwersja liczby na String
                        }
                        break;
                    case BOOLEAN:
                        cellValue = Boolean.toString(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        cellValue = cell.getCellFormula();  // Formuła jako String
                        break;
                    case BLANK:
                        cellValue = null;  // Dla pustych komórek ustawiamy null
                        break;
                    default:
                        cellValue = null;
                }

                // Przypisujemy skonwertowaną wartość do pola
                field.set(obj, cellValue);
            }
        }

        return obj;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
    }
}