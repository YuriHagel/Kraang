package kraang.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;

@UtilityClass
public class Utils {
  public static final LocalDateTime FORWARD_INFINITY =
          of(2500, 01, 01, 0, 0, 0);
}
