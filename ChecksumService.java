import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChecksumService {

    // Metoda do obliczania sumy kontrolnej dla listy obiektów
    public <T> Integer calculateChecksum(List<T> objects) {
        int checksum = 0;

        // Iteracja przez wszystkie obiekty w liście
        for (T obj : objects) {
            if (obj != null) {
                checksum += obj.hashCode();
            }
        }

        return checksum;
    }
}