package db.kraang.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.kraang.configuration.property.KraangDataConfigurationProperty;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

import static java.lang.String.valueOf;

@Configuration
@EnableJpaRepositories(basePackages = "db.kraang.repository",
        entityManagerFactoryRef = "kraangLocalContainerEntityManagerFactory",
        transactionManagerRef = "kraangPlatformTransactionManager")
@AllArgsConstructor
public class KraangDataConfiguration {
  private final KraangDataConfigurationProperty property;

  @Bean
  public HikariConfig kraangHikariConfig() {
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
  public HikariDataSource kraangDataSource() {
    return new HikariDataSource(kraangHikariConfig());
  }

  @Bean
  public HibernateJpaVendorAdapter kraangHibernateJpaAdapter() {
    var bean = new HibernateJpaVendorAdapter();
    bean.setDatabase(property.getType());
    return bean;
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean kraangLocalContainerEntityManagerFactory() {
    var bean = new LocalContainerEntityManagerFactoryBean();
    bean.setDataSource(kraangDataSource());
    bean.setJpaVendorAdapter(kraangHibernateJpaAdapter());
    bean.setPackagesToScan("db.kraang.entity");
    bean.setPersistenceUnitName(property.getPersistenceUnitName());

    var properties = new Properties();
    properties.setProperty("hibernate.dialect", property.getDialect());
    properties.setProperty("hibernate.jdbc.batch_size", String.valueOf(property.getBatchSize()));
    properties.setProperty("hibernate.jdbc.fetch_size", String.valueOf(property.getFetchSize()));
    properties.setProperty("hibernate.order_inserts", String.valueOf(property.isOrderInserts()));
    properties.setProperty("hibernate.order_updates", String.valueOf(property.isOrderUpdates()));
    properties.setProperty("hibernate.enable_lazy_load_no_trans", String.valueOf(property.isEnableLazyLoadNoTrans()));
    properties.setProperty("hibernate.show_sql", valueOf(property.isShowSql()));
    bean.setJpaProperties(properties);
    return bean;
  }

  @Bean(name = "liquibase")
  public KraangSpringLiquibase springLiquibase() {
    var bean = new KraangSpringLiquibase();
    bean.setDataSource(kraangDataSource());
    bean.setDefaultTablespace(property.getLiquibaseDefaultTablespace());
    bean.setChangeLog(property.getLiquibaseChangeLog());
    bean.setContexts("structure, ${env.type}");
    return bean;
  }

  @Bean
  @Primary
  public JpaTransactionManager kraangPlatformTransactionManager() {
    var bean = new JpaTransactionManager(kraangLocalContainerEntityManagerFactory().getObject());
    bean.setDataSource(kraangDataSource());
    bean.setPersistenceUnitName(property.getPersistenceUnitName());
    return bean;
  }
}
