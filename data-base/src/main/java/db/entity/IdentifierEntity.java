package db.entity;

import db.enums.IdentifierStatusEnm;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "identifiers")
public class IdentifierEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "s_identifiers")
  @SequenceGenerator(name = "s_identifiers", sequenceName = "s_identifiers", allocationSize = 1)
  @Column(name = "id_identifier")
  private Long id;
  @Column(name = "id_service_identifier")
  private Integer idServiceIdentifier;
  @Column(name = "v_value")
  private String msisdn;
  @Enumerated(EnumType.STRING)
  @Column(name = "v_status")
  private IdentifierStatusEnm status;
  @Column(name = "v_ext_ident")
  private Long extIdent;
  @ManyToOne
  @JoinColumn(name = "id_mnp_region")
  private MnpRegionEntity mnpRegion;
  @Column(name = "b_deleted")
  private Boolean deleted;
}
