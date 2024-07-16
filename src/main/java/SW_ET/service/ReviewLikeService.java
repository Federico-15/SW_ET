package SW_ET.service;

import SW_ET.entity.Review;
import SW_ET.entity.ReviewLike;
import SW_ET.entity.User;
import SW_ET.repository.ReviewLikeRepository;
import SW_ET.repository.ReviewRepository;
import SW_ET.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewLikeService {

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public boolean toggleLikeStatus(Long reviewId, Long userId, boolean likeStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        return reviewLikeRepository.findByUserAndReview(user, review)
                .map(reviewLike -> {
                    reviewLike.setIsLiked(likeStatus);
                    reviewLikeRepository.save(reviewLike);
                    return reviewLike.getIsLiked();
                }).orElseGet(() -> {
                    ReviewLike newReviewLike = new ReviewLike();
                    newReviewLike.setUser(user);
                    newReviewLike.setReview(review);
                    newReviewLike.setIsLiked(likeStatus);
                    reviewLikeRepository.save(newReviewLike);
                    return likeStatus;
                });
    }

    public boolean checkReviewLikeStatus(Long reviewId, Long userKeyId) {
        User user = userRepository.findById(userKeyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        return reviewLikeRepository.findByUserAndReview(user, review)
                .map(ReviewLike::getIsLiked)
                .orElse(false);
    }

    public Map<String, Integer> getLikeDislikeCount(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        int likeCount = reviewLikeRepository.countByReviewReviewIdAndIsLikedTrue(reviewId);
        int dislikeCount = reviewLikeRepository.countByReviewReviewIdAndIsLikedFalse(reviewId);

        Map<String, Integer> likeDislikeCount = new HashMap<>();
        likeDislikeCount.put("likeCount", likeCount);
        likeDislikeCount.put("dislikeCount", dislikeCount);

        return likeDislikeCount;
    }
}
