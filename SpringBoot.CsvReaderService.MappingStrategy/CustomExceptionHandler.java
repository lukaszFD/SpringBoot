private static class CustomExceptionHandler<T> implements com.opencsv.exceptions.ExceptionHandler {
    private final Class<T> clazz;

    public CustomExceptionHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void handleException(com.opencsv.exceptions.CsvException e) throws com.opencsv.exceptions.CsvException {
        if (e instanceof com.opencsv.exceptions.CsvBadConverterException) {
            com.opencsv.exceptions.CsvBadConverterException converterException = (com.opencsv.exceptions.CsvBadConverterException) e;
            String header = converterException.getLine();
            String[] headerArray = header.split(converterException.getCapturedLineDelimiter());

            int expectedColumnCount = calculateExpectedColumnCount(clazz);

            if (headerArray.length > expectedColumnCount) {
                // Tutaj możesz obsłużyć błąd, np. zalogować go
                System.err.println("Error: Too many columns in CSV file");
            } else if (headerArray.length < expectedColumnCount) {
                // Tutaj możesz obsłużyć błąd, np. zalogować go
                System.err.println("Error: Too few columns in CSV file");
            }
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
