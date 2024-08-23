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