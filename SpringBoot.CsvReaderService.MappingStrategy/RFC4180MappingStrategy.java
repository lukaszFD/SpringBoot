import com.opencsv.bean.strategy.HeaderColumnNameMappingStrategy;

public class RFC4180MappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
    private final Class<T> type;

    public RFC4180MappingStrategy(Class<T> type) {
        this.type = type;
        setType(type);
    }
}
