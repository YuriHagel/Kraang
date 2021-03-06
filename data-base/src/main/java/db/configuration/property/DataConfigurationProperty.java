package db.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "database", ignoreUnknownFields = false)
public class DataConfigurationProperty {
  private Database type;
  private String driver;
  private String dialect;
  private int maxPoolSize;
  private int batchSize;
  private int fetchSize;
  private boolean orderInserts;
  private boolean orderUpdates;
  private boolean enableLazyLoadNoTrans;
  private String url;
  private String user;
  private String password;
  private String persistenceUnitName;
  private boolean showSql;
}
