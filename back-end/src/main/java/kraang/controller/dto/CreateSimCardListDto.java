package kraang.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class CreateSimCardListDto {
  @Min(value = 1, message = "count должен быть больше или равнен 1")
  @Max(value = 500, message = "count должен быть меньше или равнен 500")
  private int count;
}
