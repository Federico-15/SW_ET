package SW_ET.repository;

import SW_ET.entity.SubRegion;
import SW_ET.entity.TravelDestination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelDestinationRepository extends JpaRepository<TravelDestination, Long> {
    List<TravelDestination> findByRegion(String regionName);

    @Query("SELECT td FROM TravelDestination td WHERE td.subRegion.subRegionId = :subRegionId")
    List<TravelDestination> findBySubRegionId(@Param("subRegionId") Long subRegionId);

    Page<TravelDestination> findBySubRegion_SubRegionId(Long subRegionId, Pageable pageable);

    Optional<TravelDestination> findByTitleAndSubRegion(String title, SubRegion subRegion);

    @Query("SELECT d FROM TravelDestination d WHERE d.region.regionId = :regionId")
    Page<TravelDestination> findByRegionId(@Param("regionId") Long regionId, Pageable pageable);

    @Query("SELECT td FROM TravelDestination td WHERE td.subRegion.subRegionId = :subRegionId AND td.id = :destinationId")
    Optional<TravelDestination> findBySubRegionIdAndDestinationId(@Param("subRegionId") Long subRegionId, @Param("destinationId") Long destinationId);

    // 검색어를 통해 여행지를 찾는 쿼리
    Page<TravelDestination> findByTitleContaining(String title, Pageable pageable);

}