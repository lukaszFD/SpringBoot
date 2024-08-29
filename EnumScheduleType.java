import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleType {
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3);

    private final int typeCode;

    public static ScheduleType fromString(String text) {
        for (ScheduleType scheduleType : ScheduleType.values()) {
            if (scheduleType.name().equalsIgnoreCase(text)) {
                return scheduleType;
            }
        }
        throw new IllegalArgumentException("Unknown schedule type: " + text);
    }
}