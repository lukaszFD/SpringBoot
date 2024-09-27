private <T> List<T> readXlsxFile(InputStream inputStream, Class<T> clazz, String sheetPattern, boolean encrypted) throws IOException {
    List<T> result = new ArrayList<>();
    try {
        Workbook workbook;

        if (encrypted) {
            try {
                // Obsługa zaszyfrowanego pliku
                POIFSFileSystem fs = new POIFSFileSystem(inputStream);
                EncryptionInfo info = new EncryptionInfo(fs);
                Decryptor decryptor = Decryptor.getInstance(info);

                // Próba otworzenia pliku w trybie tylko do odczytu, bez hasła
                if (!decryptor.verifyPassword(Decryptor.DEFAULT_PASSWORD)) {
                    throw new RuntimeException("Nie można odczytać zaszyfrowanego pliku - brak hasła.");
                }

                workbook = WorkbookFactory.create(decryptor.getDataStream(fs));
            } catch (Exception e) {
                throw new RuntimeException("Błąd podczas odczytu zaszyfrowanego pliku: " + e.getMessage(), e);
            }
        } else {
            // Odczyt standardowego pliku XLSX
            workbook = WorkbookFactory.create(inputStream);
        }

        int rowNumber = 0;
        var sheet = Optional.ofNullable(sheetPattern)
                .filter(pattern -> !pattern.isEmpty())
                .map(pattern -> {
                    for (Iterator<Sheet> it = workbook.sheetIterator(); it.hasNext(); ) {
                        Sheet currentSheet = it.next();
                        if (Pattern.compile(sheetPattern).matcher(currentSheet.getSheetName()).matches()) {
                            return currentSheet;
                        }
                    }
                    return null;
                })
                .orElseGet(() -> workbook.getSheetAt(0));

        if (sheet == null) {
            log.error("{} - ETL control ID : {}. Sheet not found", clazz.getAnnotation(Table.class).name(), controlId);
            throw new RuntimeException("Sheet not found");
        }

        for (Row row : sheet) {
            if (rowNumber++ > 0) {
                result.add(mapRowToClass(row, clazz));
            }
        }
    } catch (IOException e) {
        throw new IOException("Błąd podczas odczytu pliku: " + e.getMessage(), e);
    }

    return result;
}