package db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "identifier_group")
public class IdentifierGroupEntity {
  @Id
  @Column(name = "id_identifier_group")
  private Long id;
  @Column(name = "v_name")
  private String name;
  @Column(name = "v_description")
  private String description;
  @Column(name = "b_deleted")
  private Boolean deleted;
}
