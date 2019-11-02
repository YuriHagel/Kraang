package db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mnp_regions")
public class MnpRegionEntity {
  @Id
  @Column(name = "id_mnp_region")
  private Long id;
  @Column(name = "v_name")
  private String name;
  @Column(name = "v_number")
  private String regionCode;
}
