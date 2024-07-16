package SW_ET.repository;

import SW_ET.entity.Review;
import SW_ET.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> { // 리뷰에 대한 데이터 접근 처리

    //리뷰와 관련된 지역 정보를 포함하여 조회
    @Query("SELECT r FROM Review r JOIN FETCH r.region WHERE r.reviewId = :reviewId")
    Optional<Review> findReviewWithRegion(@Param("reviewId") Long reviewId);

    // 모든 리뷰와 관련된 지역 정보를 함께 가져오기.
    @Query("SELECT r FROM Review r JOIN FETCH r.region")
    List<Review> findAllReviewsWithRegion();

    // 지역 ID에 따라 리뷰를 조회하는 JPQL 쿼리
    @Query("SELECT r FROM Review r WHERE r.region.regionId = :regionId")
    List<Review> findByRegionId(@Param("regionId") Long regionId);

    // 리뷰 아이디 검색.
    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Optional<Review> findByReviewId(@Param("reviewId") Long reviewId);

    @Query("SELECT r FROM Review r WHERE r.subRegion.subRegionId = :subRegionId")
    List<Review> findBySubRegionId(@Param("subRegionId") Long subRegionId);

    @Query("SELECT r FROM Review r WHERE r.destination.id = :destinationId")
    List<Review> findByDestinationId(@Param("destinationId") Long destinationId);

    List<Review> findByUser(User user); // 리뷰와 유저 매핑
}
