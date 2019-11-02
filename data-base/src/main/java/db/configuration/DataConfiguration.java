package db.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.configuration.property.DataConfigurationProperty;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

import static java.lang.String.valueOf;

@Configuration
@EnableJpaRepositories(basePackages = "db",
        entityManagerFactoryRef = "localContainerEntityManagerFactory",
        transactionManagerRef = "platformTransactionManager")
@AllArgsConstructor
public class DataConfiguration {
  private final DataConfigurationProperty property;

  @Bean
  public HikariConfig forwardHikariConfig() {
    var hikariConfig = new HikariConfig();
    hikariConfig.setPoolName(property.getPersistenceUnitName() + "Pool");
    hikariConfig.setDriverClassName(property.getDriver());
    hikariConfig.setMaximumPoolSize(property.getMaxPoolSize());
    hikariConfig.setJdbcUrl(property.getUrl());
    hikariConfig.setUsername(property.getUser());
    hikariConfig.setPassword(property.getPassword());
    return hikariConfig;
  }

  @Bean
  public HikariDataSource forwardDataSource() {
    return new HikariDataSource(forwardHikariConfig());
  }

  @Bean
  public HibernateJpaVendorAdapter forwardHibernateJpaAdapter() {
    HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
    bean.setDatabase(property.getType());
    return bean;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactory() {
    var bean = new LocalContainerEntityManagerFactoryBean();
    bean.setDataSource(forwardDataSource());
    bean.setJpaVendorAdapter(forwardHibernateJpaAdapter());
    bean.setPackagesToScan("db.entity");
    bean.setPersistenceUnitName(property.getPersistenceUnitName());

    var properties = new Properties();
    properties.setProperty("hibernate.dialect", property.getDialect());
    properties.setProperty("hibernate.jdbc.batch_size", valueOf(property.getBatchSize()));
    properties.setProperty("hibernate.jdbc.fetch_size", valueOf(property.getFetchSize()));
    properties.setProperty("hibernate.order_inserts", valueOf(property.isOrderInserts()));
    properties.setProperty("hibernate.order_updates", valueOf(property.isOrderUpdates()));
    properties.setProperty("hibernate.enable_lazy_load_no_trans", valueOf(property.isEnableLazyLoadNoTrans()));
    properties.setProperty("hibernate.show_sql", valueOf(property.isShowSql()));
    bean.setJpaProperties(properties);
    return bean;
  }

  @Bean
  public JpaTransactionManager platformTransactionManager() {
    var bean = new JpaTransactionManager();
    bean.setDataSource(forwardDataSource());
    bean.setPersistenceUnitName(property.getPersistenceUnitName());
    return bean;
  }
}
