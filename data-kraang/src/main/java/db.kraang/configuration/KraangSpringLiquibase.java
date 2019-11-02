package db.kraang.configuration;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ResourceAccessor;
import lombok.Setter;

import java.sql.Connection;

public class KraangSpringLiquibase extends SpringLiquibase {
  @Setter
  private String defaultTablespace;

  @Override
  protected Database createDatabase(Connection c, ResourceAccessor resourceAccessor) throws DatabaseException {
    Database database = super.createDatabase(c, resourceAccessor);
    if (this.defaultTablespace != null) {
      database.setLiquibaseTablespaceName(this.defaultTablespace);
    }
    return database;
  }
}
