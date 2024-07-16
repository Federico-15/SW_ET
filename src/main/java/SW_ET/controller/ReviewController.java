package SW_ET.controller;

import SW_ET.config.JwtTokenProvider;
import SW_ET.dto.ReviewDto;
import SW_ET.entity.Review;
import SW_ET.service.ReviewLikeService;
import SW_ET.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/api/reviews")  // 경로를 /reviews로 변경하여 API와의 분리
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper를 빈으로 주입받음

    @Autowired
    private ReviewLikeService reviewLikeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 리뷰 상세 조회 페이지로 이동
    @GetMapping("/detail/{reviewId}")
    public String getReviewDetails(@PathVariable Long reviewId, Model model) {
        log.debug("Attempting to access details for reviewId: {}", reviewId);
        ReviewDto review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return "redirect:/error";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = review.getDatePosted().format(formatter);

        model.addAttribute("review", review);
        model.addAttribute("formattedDate", formattedDate);
        return "reviews/review_detail";
    }

    // 리뷰 등록 폼 페이지로 이동
    @GetMapping("/register")
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        String token = jwtTokenProvider.resolveToken(request);
        // 토큰이 유효한 경우, 리뷰 등록을 위한 빈 DTO를 모델에 추가
        model.addAttribute("review", new ReviewDto());
        return "reviews/register"; // "register"는 register.html 또는 등록 폼의 뷰 이름

    }

    // 리뷰 생성
    @PostMapping("/register")
    public ResponseEntity<ReviewDto> createReview(@RequestPart("review") String reviewDtoJson,
                                                  @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            log.info("Received review data: {}", reviewDtoJson);
            ReviewDto reviewDto = objectMapper.readValue(reviewDtoJson, ReviewDto.class); // JSON 문자열을 DTO 객체로 변환
            log.info("Received rating: {}", reviewDto.getRating());  // 별점 로그 출력

            if (imageFile != null && !imageFile.isEmpty()) {
                log.info("Received image file: {}", imageFile.getOriginalFilename());
                String imageUrl = reviewService.saveImage(imageFile);
                reviewDto.setImageUrl(imageUrl); // 이미지 URL을 DTO에 설정
            }

            ReviewDto savedReview = reviewService.createReview(reviewDto); // 리뷰 생성 로직 호출
            log.info("Review saved successfully: {}", savedReview);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            log.error("Exception occurred while creating the review", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 시/도에 대한 리뷰 조회
    @GetMapping("/region/{regionId}")
    public ResponseEntity<?> getReviewsByRegion(@PathVariable Long regionId) {
        List<ReviewDto> reviews = reviewService.getReviewsByRegion(regionId);
        if (reviews.isEmpty()) {
            return ResponseEntity.ok("검색된 리뷰가 없습니다.");
        }
        return ResponseEntity.ok(reviews);
    }


    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);  // 사용자 PK 추출

            reviewService.deleteReview(reviewId, userKeyId);
            return ResponseEntity.ok("Review deleted successfully.");
        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this review.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting review: " + e.getMessage());
        }
    }

    // 리뷰 수정 폼 페이지로 이동
    @GetMapping("/edit-review/{reviewId}")
    public String editReviewForm(@PathVariable Long reviewId, Model model, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);

        ReviewDto reviewDto = reviewService.getReviewById(reviewId);
        if (!reviewDto.getUserKeyId().equals(userKeyId)) {
            return "redirect:/error/unauthorized";  // 권한 없음 페이지로 리다이렉트
        }

        model.addAttribute("review", reviewDto);
        return "reviews/edit_review";  // Thymeleaf 페이지 반환
    }

    // 리뷰 수정
    @PostMapping("/edit-review/{reviewId}")
    public String updateReview(@PathVariable Long reviewId, @ModelAttribute ReviewDto reviewDto,
                               @RequestParam(value = "image", required = false) MultipartFile imageFile,
                               HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);

        if (!reviewDto.getUserKeyId().equals(userKeyId)) {
            return "redirect:/error/unauthorized";
        }

        try {
            reviewService.updateReview(reviewId, reviewDto, imageFile, userKeyId);
            return "redirect:/api/reviews/detail/" + reviewId;  // 성공적으로 수정 후 리뷰 상세 페이지로 리다이렉트
        } catch (IOException ex) {
            return "redirect:/error";  // 예외 발생 시 에러 페이지로 리다이렉트
        }
    }
}
