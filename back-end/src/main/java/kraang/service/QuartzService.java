package kraang.service;

import kraang.configuration.quartz.BaseJob;
import kraang.exception.KraangException;
import kraang.service.dto.TriggerDto;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class QuartzService {
  private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);
  public static final String MSG_TASK_RUNNING = "Задача %s уже запущена";
  public static final String MSG_TASK_NOT_FOUND = "Задача %s не найдена";
  private final Scheduler scheduler;

  /**
   * Запустить задачу сейчас
   */
  public TriggerDto runTrigger(String triggerKey) {
    updateTriggerStatus(triggerKey, TriggerStatus.RUN);
    return getTriggerByKey(triggerKey);
  }

  /**
   * "Включить" задачу, задача будет выполняться по крону
   */
  public TriggerDto resumeTrigger(String triggerKey) {
    updateTriggerStatus(triggerKey, TriggerStatus.RESUME);
    return getTriggerByKey(triggerKey);
  }

  /**
   * Остановить выполнение задачи по крону
   */
  public TriggerDto pauseTrigger(String triggerKey) {
    updateTriggerStatus(triggerKey, TriggerStatus.PAUSE);
    return getTriggerByKey(triggerKey);
  }

  /**
   * Получить все задачи
   */
  public List<TriggerDto> getAllTriggers() {
    try {
      return scheduler.getTriggerKeys(GroupMatcher.groupEquals(BaseJob.CRON_GROUP))
              .stream()
              .map(triggerKey -> triggerKey.getName())
              .map(this::getTriggerByKey)
              .collect(Collectors.toList());
    } catch (SchedulerException e) {
      logger.error("Error!", e);
      throw new KraangException(e.getMessage());
    }
  }

  public TriggerDto getTriggerByKey(String triggerKey) {
    var triggerDto = new TriggerDto();
    try {
      triggerDto.setTrigger(scheduler.getTrigger(TriggerKey.triggerKey(triggerKey, BaseJob.CRON_GROUP)));
      if (nonNull(triggerDto.getTrigger())) {
        triggerDto.setStatus(scheduler.getTriggerState(triggerDto.getTrigger().getKey()));
      }
    } catch (SchedulerException e) {
      logger.error("Error!", e);
      throw new KraangException(e.getMessage());
    }
    if (isNull(triggerDto.getTrigger()) || isNull(triggerDto.getStatus())) {
      throw new KraangException(String.format(MSG_TASK_NOT_FOUND, triggerKey));
    }
    return triggerDto;
  }

  private void updateTriggerStatus(String triggerId, TriggerStatus newStatus) {
    try {
      switch (newStatus) {
        case RUN:
          var cronTrigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerId, BaseJob.CRON_GROUP));
          var triggerKey = TriggerKey.triggerKey(BaseJob.MANUALLY_TRIGGER + triggerId, BaseJob.MANUAL_GROUP);
          var trigger = TriggerBuilder.newTrigger()
                  .withIdentity(triggerKey)
                  .forJob(cronTrigger.getJobKey())
                  .startNow()
                  .build();
          trigger.getJobDataMap().put(BaseJob.LOG_ENABLED_KEY, true);
          try {
            scheduler.scheduleJob(trigger);
          } catch (ObjectAlreadyExistsException e) {
            logger.error("Error running job {} ", trigger, e);
            throw new KraangException(String.format(MSG_TASK_RUNNING, trigger.getKey()));
          }
        case RESUME:
          scheduler.resumeTrigger(TriggerKey.triggerKey(triggerId, BaseJob.CRON_GROUP));
          break;
        case PAUSE:
          scheduler.pauseTrigger(TriggerKey.triggerKey(triggerId, BaseJob.CRON_GROUP));
          break;
        default:
          throw new KraangException("Указан несуществующий статус");
      }
    } catch (SchedulerException e) {
      logger.error("Error!", e);
      throw new KraangException(e.getMessage());
    }
  }

  public enum TriggerStatus {RUN, RESUME, PAUSE}
}
