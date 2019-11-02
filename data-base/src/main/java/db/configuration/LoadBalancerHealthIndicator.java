package db.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// test
@Component
@Slf4j
public class LoadBalancerHealthIndicator extends AbstractHealthIndicator {
  @Override
  protected void doHealthCheck(Health.Builder builder) {
    Path statusPath = Paths.get("config");
    if (Files.exists(statusPath)) {
      try (BufferedReader br = Files.newBufferedReader(statusPath)) {
        if (br.read() == "down") {
          builder.down();
          return;
        }

    }
    }
 builder.up();
  }

}
