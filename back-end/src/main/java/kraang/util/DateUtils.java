package kraang.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.util.Objects.isNull;

/**
 * @author y.hagel
 */
@UtilityClass
public class DateUtils {
  public static LocalDateTime dateToLocalDateTime(Date date) {
    return (isNull(date)) ? null : ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
  }
}
