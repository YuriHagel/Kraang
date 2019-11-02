package db.repository;

import db.entity.ServiceIdentifierEntity;
import db.enums.ServiceIdentifierStatusEnm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceIdentifierRepository extends JpaRepository<ServiceIdentifierEntity, Long> {
  @Query(value = "select count(si.id) from ServiceIdentifierEntity si " +
          "where si.deleted = false " +
          "and si.identifier.deleted = false " +
          "and si.stop > :dtStop " +
          "and si.status = 'A' " +
          "and si.identifierClass.id = :identifierClassId " +
          "and si.identifierGroup.id = :identifierGroupId " +
          "and si.idServiceInst is null " +
          "and si.identifier.mnpRegion.id =:mnpRegionId")
  int getCountUnusedIdentifiers(@Param("dtStop") LocalDateTime stop,
                                @Param("identifierClassId") Long identifierClassId,
                                @Param("identifierGroupId") Long identifierGroupId,
                                @Param("mnpRegionId") Long mnpRegionId);

  @Query(value = "select si from ServiceIdentifierEntity si " +
          "where si.deleted = false " +
          "and si.identifier.deleted = false " +
          "and si.start <= :dtStart " +
          "and si.stop > :dtStop " +
          "and si.idServiceInst is null " +
          "and si.status = :status " +
          "and si.identifier.mnpRegion.id =:mnpRegionId " +
          "and si.identifierClass.id = :identifierClassId " +
          "and si.identifierGroup.id = :identifierGroupId " +
          "and si.identifier.msisdn not in :notAvailableMsisdn " +
          "and si.identifier.msisdn like :rangePrefix% " +
          "and rownum <= :rowNum")
  List<ServiceIdentifierEntity> getUnusedIdentifiers(@Param("dtStart") LocalDateTime start,
                                                     @Param("dtStop") LocalDateTime stop,
                                                     @Param("identifierClassId") Long identifierClassId,
                                                     @Param("identifierGroupId") Long identifierGroupId,
                                                     @Param("mnpRegionId") Long mnpRegionId,
                                                     @Param("rangePrefix") String rangePrefix,
                                                     @Param("status") ServiceIdentifierStatusEnm status,
                                                     @Param("notAvailableMsisdn") List<String> notAvailableMsisdn,
                                                     @Param("rowNum") long rowNum);

  @Query(value = "select si from ServiceIdentifierEntity si " +
          "where si.deleted = false " +
          "and si.identifier.deleted = false " +
          "and si.start <= :dtStart " +
          "and si.stop > :dtStop " +
          "and si.identifier.msisdn = :msisdn")
  List<ServiceIdentifierEntity> findByMsisdn(@Param("dtStart") LocalDateTime start,
                                             @Param("dtStop") LocalDateTime stop,
                                             @Param("msisdn") String msisdn);

  @Query(value = "select s_service_identifiers.nextval from dual", nativeQuery = true)
  long fwServiceIdentifiersNext();
}
