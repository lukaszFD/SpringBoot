import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleService {

    private final GenericService<Person> personService;

    @Autowired
    public ExampleService(PersonRepository personRepository, BatchSizeStatisticsRepository statisticsRepository) {
        this.personService = new GenericService<>(personRepository, statisticsRepository);
    }

    public void exampleUsage() {
        List<Person> persons = // Pobierz listę osób do zapisania

        // Wywołanie metody saveEntitiesInParallel
        personService.saveEntitiesInParallel(persons);
    }
}
