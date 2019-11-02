package kraang.controller.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> extends AbstractResponse {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T payload;
}
