package SW_ET.repository;

import SW_ET.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE r.regionGroup.regionGroupId = :groupId")
    List<Region> findByRegionGroupId(@Param("groupId") Long groupId);

    @Query("SELECT r.regionName, sr.subRegionName FROM Region r JOIN r.subRegions sr WHERE r.regionId = :regionId AND sr.subRegionId = :subRegionId")
    List<Object[]> findRegionAndSubRegionNames(@Param("regionId") Long regionId, @Param("subRegionId") Long subRegionId);

    @Query("SELECT r FROM Region r WHERE r.regionName = :regionName")
    List<Region> findByRegionName(@Param("regionName") String regionName);

    @Query("SELECT r FROM Region r JOIN FETCH r.regionGroup WHERE lower(r.regionName) = lower(:name)")
    Region findByRegionNameWithGroup(@Param("name") String name); //  region 조회 시 regionGroup 조인

    @Query("SELECT r FROM Region r WHERE LOWER(r.regionName) = LOWER(:name)")
    Optional<Region> findByNameIgnoreCase(@Param("name") String name);
}