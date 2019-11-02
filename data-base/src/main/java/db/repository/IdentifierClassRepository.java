package db.repository;

import db.entity.IdentifierClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentifierClassRepository extends JpaRepository<IdentifierClassEntity, Long> {
}
