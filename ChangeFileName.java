import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

private <T> List<T> readFile(Class<T> clazz) throws Exception {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(localD));
    })) {
        for (Path path : stream) {
            String filename = path.getFileName().toString();
            String filePattern = ""

            if (Pattern.compile(filePattern).matcher(filename).matches()) {
                
                // Przetwarzanie pliku w zależności od jego rozszerzenia
                List<T> result = switch (FilenameUtils.getExtension(filename).toLowerCase()) {
                    case "csv" -> getCsv(clazz, feed, path);
                    default -> null;
                };

                if (result != null) {
                    // Jeżeli przetwarzanie pliku zakończyło się sukcesem, zmień jego nazwę
                    String newFilename = addDateToFilename(filename);
                    Path newPath = path.resolveSibling(newFilename);

                    Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("File renamed from {} to {}", filename, newFilename);
                }
                
                return result; // Zwraca wynik po poprawnym przetworzeniu
            }
        }
    }
    return null;
}

private String addDateToFilename(String filename) {
    // Format daty: yyyyMMdd
    String dateSuffix = new SimpleDateFormat("yyyyMMdd").format(new Date());

    // Dodanie daty przed rozszerzeniem
    int lastDotIndex = filename.lastIndexOf('.');
    if (lastDotIndex != -1) {
        return filename.substring(0, lastDotIndex) + "_" + dateSuffix + filename.substring(lastDotIndex);
    } else {
        // Jeżeli plik nie ma rozszerzenia
        return filename + "_" + dateSuffix;
    }
}