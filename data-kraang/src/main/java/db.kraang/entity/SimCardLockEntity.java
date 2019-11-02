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
@Table(name = "simcards_lock")
public class SimCardLockEntity {
  @Id
  @Column(name = "id_simcard_inst")
  private Long idSimCardInst;
  private String imsi;
  @Column(name = "remove_lock")
  private LocalDateTime removeLock;
}
