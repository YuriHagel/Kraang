package kraang.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

@UtilityClass
public class ExceptionUtil {
  public static String getConstraintViolationMessage(org.springframework.dao.DataIntegrityViolationException ce) {
    return ce.getCause()
            .getCause()
            .getMessage()
            .replace("\"", StringUtils.EMPTY)
            .replace("\n", StringUtils.EMPTY);
  }

  public static String getConstraintViolationMessage(org.hibernate.exception.ConstraintViolationException ce) {
    return ce.getCause()
            .getMessage()
            .replace("\"", "")
            .replace("\n", "");
  }

  public static String getInvalidDataAccessResourceUsageExceptionMessage(InvalidDataAccessResourceUsageException ce) {
    return ce.getCause()
            .getCause()
            .getMessage()
            .replace("\n", "");
  }
}
