package SW_ET.repository;

import SW_ET.entity.Review;
import SW_ET.entity.ReviewLike;
import SW_ET.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    // 특정 리뷰와 사용자에 대한 좋아요/싫어요 찾기
    Optional<ReviewLike> findByUserAndReview(User user, Review review);

    // 특정 리뷰에 대한 좋아요 수 세기
    int countByReviewReviewIdAndIsLikedTrue(Long reviewId);

    // 특정 리뷰에 대한 싫어요 수 세기
    int countByReviewReviewIdAndIsLikedFalse(Long reviewId);
}