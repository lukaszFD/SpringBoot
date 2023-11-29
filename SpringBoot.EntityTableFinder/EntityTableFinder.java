import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EntityTableFinder {

    public static void main(String[] args) {
        List<String> tableNames = findEntityTableNames();
        for (String tableName : tableNames) {
            System.out.println("Znaleziona tabela: " + tableName);
        }
    }

    public static List<String> findEntityTableNames() {
        List<String> tableNames = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources("ścieżka/do/folderu/model");

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File modelFolder = new File(resource.getFile());

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    private static String getClassNameFromFile(File file) {
        String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
