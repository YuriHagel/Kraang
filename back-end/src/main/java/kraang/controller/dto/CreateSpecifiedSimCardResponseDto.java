package kraang.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateSpecifiedSimCardResponseDto {
  private Long id;
  private String imsi;
  private String iccid;
}
