import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChecksumService {

    // Metoda do obliczania sumy kontrolnej dla listy obiektów
    public String calculateChecksum(List<Object> objects) throws IOException, NoSuchAlgorithmException {
        // Inicjalizacja MessageDigest dla algorytmu SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Serializacja każdego obiektu i obliczanie sumy kontrolnej
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            for (Object obj : objects) {
                oos.writeObject(obj);
                byte[] objectBytes = baos.toByteArray();
                digest.update(objectBytes);
                baos.reset(); // Reset strumienia po każdej serializacji
            }

        }

        // Pobranie wynikowego skrótu i konwersja na format heksadecymalny
        byte[] checksumBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : checksumBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}