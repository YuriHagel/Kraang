package db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "simcards")
public class SimCardEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "s_rec")
  @SequenceGenerator(name = "s_rec", sequenceName = "s_rec", allocationSize = 1)
  @Column(name = "id_rec")
  private Long id;
  @Column(name = "id_simcard_inst")
  private Long idSimCardInst;
  @ManyToOne
  @JoinColumn(name = "id_identifier")
  private IdentifierEntity identifier;
  @Column(name = "v_imsi")
  private String imsi;
  @Column(name = "v_sim_card")
  private String iccid;
  @Column(name = "dt_start")
  private LocalDateTime start;
  @Column(name = "dt_stop")
  private LocalDateTime stop;
  @Column(name = "id_point_of_sale_inst")
  private Integer idPointOfSaleInst;
  @Column(name = "id_manager")
  private Integer idManager;
  @Column(name = "id_simcard_type")
  private Integer idSimCardType;
  @Column(name = "dt_insert")
  private LocalDateTime dtInsert;
  @Column(name = "b_deleted")
  private Boolean deleted;
}
