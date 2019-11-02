package db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "service_identifiers_base")
public class ServiceIdentifierBaseEntity {
  @Id
  @Column(name = "id_service_identifier_inst")
  private Long idServiceIdentifierInst;
}
