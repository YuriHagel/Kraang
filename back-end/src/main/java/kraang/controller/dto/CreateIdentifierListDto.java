package kraang.controller.dto;

import kraang.annotation.IdentifierClassExists;
import kraang.annotation.IdentifierGroupExists;
import kraang.annotation.MnpRegionExists;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateIdentifierListDto {
  @Min(value = 1, message = "count должен быть больше или равнен 1")
  @Max(value = 500, message = "count должен быть меньше или равнен 500")
  private int count;
  @IdentifierClassExists
  private int identifierClass;
  @IdentifierGroupExists
  private int identifierGroup;
  @MnpRegionExists
  private String identifierRegionId;
}
