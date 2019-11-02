package kraang.controller.dto;

import kraang.annotation.SimCardNotExists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@Builder
@SimCardNotExists
public class CreateSpecifiedSimCardDto {
  @Min(value = 350779000000000L, message = "imsi не соответствует маске 350779*********")
  @Max(value = 350779999999999L, message = "imsi не соответствует маске 350779*********")
  private long imsi;
  @Min(value = 800000000000000000L, message = "iccid не соответствует маске 8000**************")
  @Max(value = 800099999999999999L, message = "iccid не соответствует маске 8000**************")
  private long iccid;
}
