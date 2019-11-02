package db.repository;

import db.entity.ServiceIdentifierBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceIdentifierBaseRepository extends JpaRepository<ServiceIdentifierBaseEntity, Long> {
}
