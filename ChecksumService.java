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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChecksumService {

    // Metoda do obliczania sumy kontrolnej SHA-256 dla listy obiektów
    public <T> long calculateChecksum(List<T> objects) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        for (T obj : objects) {
            if (obj != null) {
                byte[] bytes = obj.toString().getBytes(); // Można dostosować serializację
                digest.update(bytes);
            }
        }

        byte[] hash = digest.digest();
        long checksum = 0;

        // Konwersja pierwszych 8 bajtów skrótu na long (można dostosować do dowolnej długości)
        for (int i = 0; i < 8; i++) {
            checksum = (checksum << 8) | (hash[i] & 0xFF);
        }

        return Math.abs(checksum);  // Zapewnienie, że wynik jest dodatni
    }
}