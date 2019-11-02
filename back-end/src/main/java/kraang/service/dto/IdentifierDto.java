package kraang.service.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IdentifierDto {
  private String msisdn;
  private Long idIdentifier;
  private String regionCode;
}
