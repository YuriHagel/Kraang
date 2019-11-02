package db.entity;

import db.enums.ServiceIdentifierStatusEnm;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_identifiers")
public class ServiceIdentifierEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "s_rec")
  @SequenceGenerator(name = "s_rec", sequenceName = "s_rec", allocationSize = 1)
  @Column(name = "id_rec")
  private Long id;
  @Column(name = "id_service_identifier_inst")
  private Long idServiceIdentifierInst;
  @Column(name = "id_service_identifier")
  private Long idServiceIdentifier;
  @ManyToOne
  @JoinColumn(name = "id_identifier")
  private IdentifierEntity identifier;
  @ManyToOne
  @JoinColumn(name = "id_identifier_class")
  private IdentifierClassEntity identifierClass;
  @ManyToOne
  @JoinColumn(name = "id_identifier_group")
  private IdentifierGroupEntity identifierGroup;
  @Enumerated(EnumType.STRING)
  @Column(name = "v_status")
  private ServiceIdentifierStatusEnm status;
  @Column(name = "dt_start")
  private LocalDateTime start;
  @Column(name = "dt_updated")
  private LocalDateTime updated;
  @Column(name = "dt_stop")
  private LocalDateTime stop;
  @Column(name = "id_service_inst")
  private Long idServiceInst;
  @Column(name = "b_base")
  private Boolean base;
  @Column(name = "v_ext_ident")
  private String extIdent;
  @Column(name = "id_manager")
  private int idManager;
  @Column(name = "b_deleted")
  private Boolean deleted;
}
