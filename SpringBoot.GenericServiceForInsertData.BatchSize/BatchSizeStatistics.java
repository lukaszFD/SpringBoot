import java.time.Duration;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "batch_size_statistics")
public class BatchSizeStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int batchSize;
    private int dataSize;
    private Duration executionTime;
    private String tableName;
    private int numberOfThreads;

    // Gettery i settery
}
