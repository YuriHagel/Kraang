package db.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IdentifierStatusEnm {
  W("в режиме ожидания(в отстойнике)"),
  N("новый"),
  U("используется"),
  R("зарезервирован");
  private final String description;
}
