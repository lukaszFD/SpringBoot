import org.springframework.stereotype.Component;

@Component
public class ThreadLocalVariables {

    private static final ThreadLocal<Map<String, Object>> threadLocalVariables =
        ThreadLocal.withInitial(HashMap::new);

    public Object get(String key) {
        return threadLocalVariables.get().get(key);
    }

    public void set(String key, Object value) {
        threadLocalVariables.get().put(key, value);
    }

    public void clear() {
        threadLocalVariables.get().clear();
    }
}


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThreadLocalVariables {

    private static final ThreadLocal<Map<String, Object>> threadLocalVariables =
        ThreadLocal.withInitial(HashMap::new);

    // Metoda ogólna zwracająca Object
    public Object get(String key) {
        return threadLocalVariables.get().get(key);
    }

    // Metoda zwracająca Integer
    public Integer getInteger(String key) {
        try {
            Object value = threadLocalVariables.get().get(key);
            if (value instanceof Integer) {
                return (Integer) value;
            }
            return null; // Lub rzuć wyjątek, jeśli nie jest Integer
        } finally {
            clear(); // Automatycznie czyści ThreadLocal po pobraniu wartości
        }
    }

    // Metoda zwracająca String
    public String getString(String key) {
        try {
            Object value = threadLocalVariables.get().get(key);
            if (value instanceof String) {
                return (String) value;
            }
            return null; // Lub rzuć wyjątek, jeśli nie jest String
        } finally {
            clear(); // Automatycznie czyści ThreadLocal po pobraniu wartości
        }
    }

    public void set(String key, Object value) {
        threadLocalVariables.get().put(key, value);
    }

    public void clear() {
        threadLocalVariables.get().clear();
    }
}