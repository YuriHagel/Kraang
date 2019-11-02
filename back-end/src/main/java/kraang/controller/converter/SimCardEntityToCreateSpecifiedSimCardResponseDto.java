package kraang.controller.converter;

import db.entity.SimCardEntity;
import kraang.controller.dto.CreateSpecifiedSimCardResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SimCardEntityToCreateSpecifiedSimCardResponseDto
        implements Converter<SimCardEntity, CreateSpecifiedSimCardResponseDto> {
  @Override
  public CreateSpecifiedSimCardResponseDto convert(SimCardEntity source) {
    var record = new CreateSpecifiedSimCardResponseDto();
    record.setId(source.getIdSimCardInst());
    record.setImsi(source.getImsi());
    record.setIccid(source.getIccid());
    return record;
  }
}
