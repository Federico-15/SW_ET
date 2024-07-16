package SW_ET.repository;

import SW_ET.entity.Favorite;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    boolean existsByUserAndTravelDestination(User user, TravelDestination travelDestination);
    void deleteByUserAndTravelDestination(User user, TravelDestination travelDestination);
    Optional<Favorite> findByUser_UserKeyIdAndTravelDestination_Id(Long userKeyId, Long destinationId);


}
