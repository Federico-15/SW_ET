package SW_ET.repository;

import SW_ET.entity.Region;
import SW_ET.entity.SubRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {
    @Query("SELECT sr FROM SubRegion sr WHERE sr.region.regionId = :regionId")
    List<SubRegion> findByRegionId(@Param("regionId") Long regionId);

    // 메소드 반환 타입을 Optional<SubRegion>으로 변경

    @Query("SELECT sr FROM SubRegion sr WHERE sr.subRegionName = :subRegionName")
    List<SubRegion> findBySubRegionName(@Param("subRegionName") String subRegionName);

}