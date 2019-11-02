package kraang.controller.converter;

import db.entity.IdentifierEntity;
import db.entity.MnpRegionEntity;
import db.entity.ServiceIdentifierEntity;
import kraang.service.dto.IdentifierDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Optional.of;

@Component
public class ServiceIdentifierEntityToIdentifierDto implements Converter<ServiceIdentifierEntity, IdentifierDto> {
  @Override
  public IdentifierDto convert(ServiceIdentifierEntity source) {
    var record = new IdentifierDto();
    record.setMsisdn(of(source.getIdentifier())
            .map(IdentifierEntity::getMsisdn)
            .orElse(null));
    record.setIdIdentifier(of(source.getIdentifier())
            .map(IdentifierEntity::getId)
            .orElse(null));
    record.setRegionCode(of(source.getIdentifier())
            .map(IdentifierEntity::getMnpRegion)
            .map(MnpRegionEntity::getRegionCode)
            .orElse(null));
    return record;
  }
}
