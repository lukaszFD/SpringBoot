
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public <T> void extractedData(Class<T> entityClass, JpaRepository<T, ?> repository) {
    String rawFileName = entityClass.getAnnotation(Table.class).name();
    String safeFileName = rawFileName.replaceAll("[^a-zA-Z0-9._-]", "_") // Usuwa niedozwolone znaki
                                     + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) 
                                     + ".csv";

    Path safePath = Paths.get(localDir, safeFileName).normalize();

    // Sprawdzamy, czy ścieżka nadal znajduje się w localDir
    if (!safePath.startsWith(Paths.get(localDir))) {
        throw new SecurityException("Attempted Path Traversal attack: " + safePath);
    }

    try (CSVWriter writer = new CSVWriter(new FileWriter(safePath.toFile()))) {
        // Reszta logiki generowania CSV...
    } catch (Exception e) {
        log.error("Error while generating data for {} - {}", safeFileName, entityClass.getAnnotation(Table.class).name(), e);
    }
}


package com.example.export;

import com.example.entity.YourEntity;
import com.example.repository.YourEntityRepository;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CsvExportService {

    private final YourEntityRepository entityRepository;

    public CsvExportService(YourEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public String exportToCsv() throws IOException {
        List<YourEntity> entities = entityRepository.findAll();

        if (entities.isEmpty()) {
            return "No data to export.";
        }

        // Generowanie nazwy pliku
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "RECON_" + timestamp + ".csv";
        String filePath = Paths.get(System.getProperty("user.home"), fileName).toString();

        // Pobranie nazw pól encji
        Field[] fields = YourEntity.class.getDeclaredFields();

        // Tworzenie pliku CSV z OpenCSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Tworzenie nagłówka z nazwami pól
            String[] headers = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                headers[i] = fields[i].getName();
            }
            writer.writeNext(headers);

            // Zapisywanie danych
            for (YourEntity entity : entities) {
                String[] rowData = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true); // Umożliwia dostęp do prywatnych pól
                    Object value = fields[i].get(entity);
                    rowData[i] = value != null ? value.toString() : "";
                }
                writer.writeNext(rowData);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing entity fields", e);
        }

        return filePath;
    }
}