package db.repository;

import db.entity.IdentifierGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentifierGroupRepository extends JpaRepository<IdentifierGroupEntity, Long> {
}
