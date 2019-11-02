package kraang.controller.dto.quartz;

import kraang.annotation.CronValidator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class QuartzTriggerDto {
  private String id;
  private String name;
  @CronValidator
  private String cron;
  private LocalDateTime nextDate;
  private LocalDateTime previousDate;
  private String status;
  private boolean logEnabled;
}
