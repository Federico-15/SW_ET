package SW_ET.repository;

import SW_ET.entity.ReviewImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImages, Long> {

}