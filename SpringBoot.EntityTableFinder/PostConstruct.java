import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class YourScriptRunner {

    private final ScriptService scriptService;

    // Wstrzykiwanie zależności, tutaj możesz wstrzyknąć swoją usługę wykonującą skrypt
    @Autowired
    public YourScriptRunner(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    // Oznaczona adnotacją @PostConstruct metoda zostanie wykonana automatycznie po utworzeniu komponentu
    @PostConstruct
    public void runScriptOnce() {
        // Sprawdź, czy skrypt został już uruchomiony
        if (!scriptService.isScriptExecuted()) {
            // Tutaj umieść kod skryptu
            System.out.println("Uruchamiam skrypt...");

            // Oznacz, że skrypt został uruchomiony
            scriptService.markScriptAsExecuted();
        }
    }
}
