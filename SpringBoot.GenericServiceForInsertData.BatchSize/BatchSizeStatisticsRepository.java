import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchSizeStatisticsRepository extends JpaRepository<BatchSizeStatistics, Long> {

    List<BatchSizeStatistics> findByTableName(String tableName);
}
