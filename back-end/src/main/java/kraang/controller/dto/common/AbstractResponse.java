package kraang.controller.dto.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class AbstractResponse {
  protected String message = HttpStatus.OK.getReasonPhrase();
}
