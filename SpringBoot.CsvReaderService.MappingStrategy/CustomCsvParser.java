    private static class CustomCsvParser implements ICSVParser {
        private final CSVParser defaultParser = new CSVParser();

        @Override
        public String[] parseLineMulti(String nextLine) throws IOException {
            try {
                return defaultParser.parseLineMulti(nextLine);
            } catch (CsvException e) {
                LOGGER.warning("Error parsing CSV record: " + e.getMessage());
                return null; // Pomijaj błędne rekordy
            }
        }

        @Override
        public String[] parseLine(String nextLine, boolean multi) throws IOException {
            try {
                return defaultParser.parseLine(nextLine, multi);
            } catch (CsvException e) {
                LOGGER.warning("Error parsing CSV record: " + e.getMessage());
                return null; // Pomijaj błędne rekordy
            }
        }
    }