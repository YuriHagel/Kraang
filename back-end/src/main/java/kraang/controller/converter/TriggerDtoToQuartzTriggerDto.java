package kraang.controller.converter;

import kraang.configuration.quartz.BaseJob;
import kraang.controller.dto.quartz.QuartzTriggerDto;
import kraang.service.dto.TriggerDto;
import kraang.util.DateUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TriggerDtoToQuartzTriggerDto implements Converter<TriggerDto, QuartzTriggerDto> {
  @Override
  public QuartzTriggerDto convert(TriggerDto source) {
    var record = new QuartzTriggerDto();
    record.setId(source.getTrigger().getKey().getName());
    record.setName(source.getTrigger().getDescription());
    if (source.getTrigger() instanceof CronTriggerImpl) {
      record.setCron(((CronTriggerImpl) source.getTrigger()).getCronExpression());
    }
    record.setPreviousDate(DateUtils.dateToLocalDateTime(source.getTrigger().getPreviousFireTime()));
    record.setNextDate(DateUtils.dateToLocalDateTime(source.getTrigger().getNextFireTime()));
    record.setStatus(source.getStatus().name());
    record.setLogEnabled(source.getTrigger()
            .getJobDataMap()
            .getBoolean(BaseJob.LOG_ENABLED_KEY));
    return record;
  }
}
