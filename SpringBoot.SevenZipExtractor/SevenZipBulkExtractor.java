import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SevenZipBulkExtractor {
    public static void main(String[] args) {
        // Podaj ścieżkę do folderu zawierającego pliki .7z
        String folderPath = "ścieżka/do/folderu";

        extractAll7zFiles(folderPath);
    }

    public static void extractAll7zFiles(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".7z"));

            if (files != null) {
                for (File file : files) {
                    extract7zFile(file.getAbsolutePath(), folder.getAbsolutePath());
                }
            } else {
                System.out.println("Brak plików .7z w folderze.");
            }
        } else {
            System.out.println("Podana ścieżka nie istnieje lub nie jest folderem.");
        }
    }

    public static void extract7zFile(String inputFilePath, String outputDirectory) {
        try (SevenZFile sevenZFile = new SevenZFile(new File(inputFilePath))) {
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    // Jeżeli wpis jest katalogiem, utwórz odpowiedni katalog
                    File directory = new File(outputDirectory, entry.getName());
                    directory.mkdirs();
                } else {
                    // Jeżeli wpis jest plikiem, wypakuj go
                    File file = new File(outputDirectory, entry.getName());
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] content = new byte[(int) entry.getSize()];
                        sevenZFile.read(content, 0, content.length);
                        fos.write(content);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
