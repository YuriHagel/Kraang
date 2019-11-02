package db.repository;


import db.entity.SimCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SimCardRepository extends JpaRepository<SimCardEntity, Long> {
  @Query(value = "select tcs_s_testing_v_msin_id.nextval from dual", nativeQuery = true)
  long sequenceTestingImsiNext();

  @Query(value = "select tcs_s_testing_v_sim_card.nextval from dual", nativeQuery = true)
  long sequenceTestingIccIdNext();

  @Query(value = "select s_simcards.nextval from dual", nativeQuery = true)
  long sequenceFwSimCardsNext();

  @Query(value = "select sim " +
          "from SimCardEntity sim" +
          " where sim.start <= :dtStart" +
          " and sim.stop > :dtStop" +
          " and (sim.imsi = :imsi or sim.iccid = :iccid)")
  List<SimCardEntity> findByImsiOrIccId(@Param("dtStart") LocalDateTime start,
                                        @Param("dtStop") LocalDateTime stop,
                                        @Param("imsi") String imsi,
                                        @Param("iccid") String iccid);

  @Query(value = "select sim " +
          "from SimCardEntity sim" +
          " where sim.start <= :dtStart" +
          " and sim.stop > :dtStop" +
          " and sim.identifier = null" +
          " and sim.deleted = false" +
          " and sim.idSimCardInst not in :notIdSimCardInst" +
          " and rownum <= :rowNum")
  List<SimCardEntity> findByAvailableSimCard(@Param("dtStart") LocalDateTime start,
                                             @Param("dtStop") LocalDateTime stop,
                                             @Param("rowNum") long rowNum,
                                             @Param("notIdSimCardInst") List<Long> notIdSimCardInst);

  @Query(value = "select sim " +
          "from SimCardEntity sim" +
          " where sim.start <= :dtStart" +
          " and sim.stop > :dtStop" +
          " and sim.identifier = null" +
          " and sim.deleted = false" +
          " and sim.idSimCardInst not in :notIdSimCardInst" +
          " and sim.idSimCardInst in :inIdSimCardInst" +
          " and rownum <= :rowNum")
  List<SimCardEntity> findByAvailableSimCard(@Param("dtStart") LocalDateTime start,
                                             @Param("dtStop") LocalDateTime stop,
                                             @Param("rowNum") long rowNum,
                                             @Param("notIdSimCardInst") List<Long> notIdSimCardInst,
                                             @Param("inIdSimCardInst") List<Long> inIdSimCardInst);

  @Query(value = "select sim " +
          "from SimCardEntity sim" +
          " where sim.start <= :dtStart" +
          " and sim.stop > :dtStop" +
          " and sim.imsi in :imsiList")
  List<SimCardEntity> findByImsiIn(@Param("dtStart") LocalDateTime start,
                                   @Param("dtStop") LocalDateTime stop,
                                   @Param("imsiList") List<String> imsiList);

  @Query(value = "select sim " +
          "from SimCardEntity sim" +
          " where sim.start <= :dtStart" +
          " and sim.stop > :dtStop" +
          " and sim.idSimCardInst = :idSimCardInst")
  SimCardEntity findByIdSimCardInst(@Param("dtStart") LocalDateTime start,
                                    @Param("dtStop") LocalDateTime stop,
                                    @Param("idSimCardInst") Long idSimCardInst);
}