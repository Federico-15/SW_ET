package SW_ET.controller;

import SW_ET.config.JwtTokenProvider;
import SW_ET.service.ReviewLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewLikeController {

    @Autowired
    private ReviewLikeService reviewLikeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<String> toggleLikeStatus(@PathVariable Long reviewId, @RequestParam boolean likeStatus, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userId = jwtTokenProvider.getUserKeyIdFromToken(token);
            boolean currentStatus = reviewLikeService.toggleLikeStatus(reviewId, userId, likeStatus);
            return ResponseEntity.ok("Like status updated to: " + currentStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error toggling like status: " + e.getMessage());
        }
    }

    @GetMapping("/{reviewId}/likes/status")
    public ResponseEntity<?> checkLikeStatus(@PathVariable Long reviewId, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userId = jwtTokenProvider.getUserKeyIdFromToken(token);
            boolean likeStatus = reviewLikeService.checkReviewLikeStatus(reviewId, userId);
            return ResponseEntity.ok(likeStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking like status: " + e.getMessage());
        }
    }

    @GetMapping("/{reviewId}/likes/counts")
    public ResponseEntity<Map<String, Integer>> getLikeDislikeCounts(@PathVariable Long reviewId) {
        try {
            Map<String, Integer> counts = reviewLikeService.getLikeDislikeCount(reviewId);
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
