package kraang.controller.dto;

import kraang.annotation.IdentifierClassExists;
import kraang.annotation.IdentifierGroupExists;
import kraang.annotation.MnpRegionExists;
import kraang.annotation.RangePrefixValid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class AvailableIdentifierDto {
  @Min(value = 1, message = "identifierCount должен быть больше или равнен 1")
  @Max(value = 5, message = "identifierCount должен быть меньше или равнен 5")
  int identifierCount;
  @Min(value = 3, message = "timeToLockSeconds должен быть больше или равнен 3")
  @Max(value = 120, message = "timeToLockSeconds должен быть меньше или равнен 120")
  int timeToLockSeconds;
  @MnpRegionExists
  String mnpRegionCode;
  @IdentifierClassExists
  private int identifierClass;
  @IdentifierGroupExists
  private int identifierGroup;
  @RangePrefixValid
  private int rangePrefix;
}
