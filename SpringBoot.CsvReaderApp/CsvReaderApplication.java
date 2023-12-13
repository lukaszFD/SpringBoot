@SpringBootApplication
public class CsvReaderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CsvReaderApplication.class, args);

        // Ścieżka do pliku CSV
        String csvFilePath1 = "ścieżka/do/pliku1.csv";
        String csvFilePath2 = "ścieżka/do/pliku2.csv";

        // Pobierz beana CsvReaderService
        CsvReaderService csvReaderService = context.getBean(CsvReaderService.class);

        // Wczytaj dane z pliku CSV do listy obiektów dla YourClass1
        List<YourClass1> yourClassList1 = csvReaderService.readCsvFile(new File(csvFilePath1), YourClass1.class);
        if (yourClassList1 != null) {
            for (YourClass1 yourClass : yourClassList1) {
                System.out.println(yourClass);
            }
        }

        // Wczytaj dane z pliku CSV do listy obiektów dla YourClass2
        List<YourClass2> yourClassList2 = csvReaderService.readCsvFile(new File(csvFilePath2), YourClass2.class);
        if (yourClassList2 != null) {
            for (YourClass2 yourClass : yourClassList2) {
                System.out.println(yourClass);
            }
        }
    }
}
