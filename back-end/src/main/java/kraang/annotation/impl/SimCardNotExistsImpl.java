package kraang.annotation.impl;

import db.repository.SimCardRepository;
import kraang.ApplicationContextProvider;
import kraang.annotation.SimCardNotExists;
import kraang.controller.dto.CreateSpecifiedSimCardDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static org.springframework.util.CollectionUtils.isEmpty;

public class SimCardNotExistsImpl implements ConstraintValidator<SimCardNotExists, CreateSpecifiedSimCardDto> {
  private static final String ERROR_MESSAGE = "Указанные imsi/icсid используются:";

  @Override
  public void initialize(SimCardNotExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(CreateSpecifiedSimCardDto value, ConstraintValidatorContext context) {
    var simCardRepository = ApplicationContextProvider
            .getApplicationContext()
            .getBean(SimCardRepository.class);

    var simCardsFromDb = simCardRepository.findByImsiOrIccId(LocalDateTime.now(), LocalDateTime.now(),
            String.valueOf(value.getImsi()),
            String.valueOf(value.getIccid()));

    if (!isEmpty(simCardsFromDb)) {
      String message = ERROR_MESSAGE;
      for (var sim : simCardsFromDb) {
        message += " [id=" + sim.getId() + ";imsi=" + sim.getImsi() + "; iccid=" + sim.getIccid() + "]";
      }
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message)
              .addConstraintViolation();
      return false;
    }
    return true;
  }
}
