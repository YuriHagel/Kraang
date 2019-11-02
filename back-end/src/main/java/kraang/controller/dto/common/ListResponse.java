package kraang.controller.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

@Getter
public class ListResponse<T> extends AbstractResponse {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<T> payload;

  public ListResponse(T payload) {
    this.payload = singletonList(payload);
  }

  public ListResponse(Collection<T> payload) {
    this.payload = new ArrayList(payload);
  }

  public boolean add(T elm) {
    return this.payload.add(elm);
  }
}
