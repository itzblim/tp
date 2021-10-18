package teethirteen.modbook.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeUtil {

    /**
     * Returns a case-insensitive DateTimeFormatter according to the given pattern
     */
    public static DateTimeFormatter buildFormatter(String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter();
    }
}
