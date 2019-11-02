package kraang.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.quartz.Trigger;
@Getter
@Setter
public class TriggerDto {
  private Trigger trigger;
  private Trigger.TriggerState status;
}
