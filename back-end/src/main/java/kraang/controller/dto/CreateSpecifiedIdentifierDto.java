package kraang.controller.dto;

import kraang.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CreateSpecifiedIdentifierDto {
  @IdentifierValid
  @IdentifierNotExists
  private String identifier;
  @IdentifierClassExists
  private int identifierClass;
  @IdentifierGroupExists
  private int identifierGroup;
  @MnpRegionExists
  private String mnpRegionCode;
}
