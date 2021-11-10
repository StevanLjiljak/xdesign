package com.example.xdesign.repository;

import com.example.xdesign.model.Mountain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScottishHillsRepository extends JpaRepository<Mountain, Long> {

  List<Mountain> findByHillCategory(String hillCategory);


  // from User u select u.name order by u.name
  // order by m.height :sortHeight, m.name :sortName

  @Query("from mountain m order by m.height, m.name")
  List<Mountain> findByHeightGreaterThan(@Param("sortHeight") String sortHeight, @Param("sortName") String sortName);

//    public ChrPos findByChrPosId(ChrPosId chrPosId);
//
//    public List<ChrPos> findByChrPosIdChr(String chr);
//
//    @Query("from chrpos where chrPosId.pos between :pos1 and :pos2")
//    public List<ChrPos> getBetweenPos(@Param("pos1") Long pos1, @Param("pos2") Long pos2);

    /*
        SELECT *
        FROM persida.chrpos cp1
        WHERE cp1.ref IN (
		    SELECT cp2.ref
		    FROM persida.chrpos cp2
		    GROUP BY cp2.ref
		    HAVING COUNT(cp2.ref) > 1
        ) GROUP BY cp1.alt
        HAVING COUNT(cp1.alt) = 1;
    */

//    @Query("from chrpos cp1 " +
//            "where cp1.ref IN ( " +
//            "select cp2.ref from chrpos cp2 " +
//            "group by cp2.ref " +
//            "having count(cp2.ref) > 1 " +
//            ") group by cp1.alt " +
//            " having count(cp1.alt) = 1")
//    public List<ChrPos> getDeletion();
//
//
//    @Query("from chrpos")
//    public List<ChrPos> getVariant(String gen);
}
