package kraang.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class AvailableSimCardDto {
  @Min(value = 1, message = "simCardCount должен быть больше или равнен 1")
  @Max(value = 5, message = "simCardCount должен быть меньше или равнен 5")
  int simCardCount;
  @Min(value = 3, message = "timeToLockSeconds должен быть больше или равнен 3")
  @Max(value = 120, message = "timeToLockSeconds должен быть меньше или равнен 120")
  int timeToLockSeconds;
  Boolean provisioningEnabled;
}
