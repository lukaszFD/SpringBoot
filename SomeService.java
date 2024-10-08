import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    @Autowired
    private ThreadLocalVariables threadLocalVariables;

    public void executeExternalMethod() {
        // ustawienie zmiennej w ThreadLocal
        threadLocalVariables.set("exampleKey", "exampleValue");

        // Stworzenie instancji klasy zewnętrznej
        ExternalClass externalClass = new ExternalClass();

        // Przekazanie ThreadLocalVariables do metody klasy zewnętrznej
        externalClass.processData(threadLocalVariables);
    }
}

public class ExternalClass {

    public void processData(ThreadLocalVariables threadLocalVariables) {
        // Pobranie zmiennej z ThreadLocal
        Object value = threadLocalVariables.get("exampleKey");

        // Użycie zmiennej
        System.out.println("Processed Value: " + value);
    }
}

@Service
public class SomeService {

    @Autowired
    private ThreadLocalVariables threadLocalVariables;

    public void someMethod() {
        try {
            // ustawienie zmiennej
            threadLocalVariables.set("exampleKey", "exampleValue");

            // pobranie zmiennej
            Object value = threadLocalVariables.get("exampleKey");

            // użycie zmiennej
            System.out.println("Value: " + value);
        } finally {
            // wyczyszczenie zmiennych po zakończeniu operacji
            threadLocalVariables.clear();
        }
    }
}