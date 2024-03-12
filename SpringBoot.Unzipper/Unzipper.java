import java.io.*;
import java.util.zip.*;

public class Unzipper {

    public static void main(String[] args) {
        String zipFilePath = "ścieżka/do/twojego/pliku.zip";

        try {
            File unpackedFile = unzip(new File(zipFilePath));
            System.out.println("Unzip completed successfully. Unpacked file: " + unpackedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File unzip(File zipFile) throws IOException {
        String destDir = zipFile.getParent();
        File unpackedFile = null;

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                String filePath = destDir + File.separator + entryName;
                unpackedFile = new File(filePath);

                if (entry.isDirectory()) {
                    // Tworzenie katalogu, jeśli jest to katalog
                    new File(filePath).mkdirs();
                } else {
                    // Tworzenie pliku i zapis danych
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
                        byte[] bytesIn = new byte[4096];
                        int read;
                        while ((read = zipInputStream.read(bytesIn)) != -1) {
                            bos.write(bytesIn, 0, read);
                        }
                    }
                }

                zipInputStream.closeEntry();
            }
        }

        return unpackedFile;
    }
}
