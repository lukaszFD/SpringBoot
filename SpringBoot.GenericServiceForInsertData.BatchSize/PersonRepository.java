import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Możesz dodać niestandardowe metody zapytań, jeśli są potrzebne
}
