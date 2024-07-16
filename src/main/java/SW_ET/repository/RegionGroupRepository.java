package SW_ET.repository;

import SW_ET.entity.RegionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionGroupRepository extends JpaRepository<RegionGroup, Long> {
    Optional<RegionGroup> findByRegionGroupName(String regionGroupName);
}