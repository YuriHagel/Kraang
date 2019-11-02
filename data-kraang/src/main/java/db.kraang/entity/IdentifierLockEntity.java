package db.kraang.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "identifiers_lock")
public class IdentifierLockEntity {
  @Id
  @Column(name = "msisdn")
  private String msisdn;
  @Column(name = "remove_lock")
  private LocalDateTime removeLock;
}
