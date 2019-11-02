package db.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Health Check Connection Pool Configuration
 */
@Configuration
public class HealthCheckConfig {

  @Bean("")
  @ConfigurationProperties("")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }
}
