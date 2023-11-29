import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class EntityTableFinder {

    public static void main(String[] args) {
        List<String> tableNames = findEntityTableNames("ścieżka/do/folderu/model");
        for (String tableName : tableNames) {
            System.out.println("Znaleziona tabela: " + tableName);
        }
    }

    public static List<String> findEntityTableNames(String modelFolderPath) {
        List<String> tableNames = new ArrayList<>();

        File modelFolder = new File(modelFolderPath);
        if (modelFolder.isDirectory()) {
            File[] files = modelFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith("entity.java")) {
                        try {
                            Class<?> entityClass = Class.forName(getClassNameFromFile(file));
                            Annotation[] annotations = entityClass.getAnnotations();
                            for (Annotation annotation : annotations) {
                                if (annotation.annotationType().getSimpleName().equals("Table")) {
                                    String tableName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
                                    tableNames.add(tableName);
                                }
                            }
                        } catch (ClassNotFoundException | ReflectiveOperationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return tableNames;
    }

    private static String getClassNameFromFile(File file) {
        String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
