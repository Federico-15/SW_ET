package SW_ET.service;

import SW_ET.dto.ReviewDto;
import SW_ET.entity.*;
import SW_ET.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mysql.cj.conf.PropertyKey.logger;

@Service
@Slf4j
public class ReviewService {

    private final Path fileStorageLocation;

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
                         RegionRepository regionRepository, SubRegionRepository subRegionRepository) throws IOException {
        this.reviewRepository = reviewRepository;
        // 프로젝트 내 상대 경로 설정
        String dir = System.getProperty("user.dir") + "/src/main/resources/static/images";  // 프로젝트 경로를 기준으로 폴더 지정
        this.fileStorageLocation = Paths.get(dir).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);  // 디렉터리가 없으면 생성
        log.info("Images are stored at: {}", this.fileStorageLocation.toString());
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IOException("File is empty");
        }

        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = this.fileStorageLocation.resolve(uniqueFilename);
        Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + uniqueFilename;  // HTTP URL 반환
    }


    public ReviewDto createReview(ReviewDto reviewDto) {
        validateRating(reviewDto.getRating());  // 별점 유효성 검사 호출

        log.info("Looking up user by key ID: {}", reviewDto.getUserKeyId());
        User user = userRepository.findById(reviewDto.getUserKeyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        log.info("Found user: {}", user.getUserNickName());
        Region region = regionRepository.findById(reviewDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));
        SubRegion subRegion = subRegionRepository.findById(reviewDto.getSubRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-region ID"));

        Review review = new Review();
        review.setUser(user);
        review.setRegion(region);
        review.setSubRegion(subRegion);
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewText(reviewDto.getReviewText());
        review.setUseYn(reviewDto.getUseYn());
        review.setDatePosted(LocalDateTime.now());
        review.setImageUrl(reviewDto.getImageUrl()); // 이미지 URL 설정
        review.setRating(reviewDto.getRating()); // 별점 정보 설정

        review = reviewRepository.save(review);

        return convertToDto(review);
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return convertToDto(review);
    }

    // 리뷰 수정
    public ReviewDto updateReview(Long id, ReviewDto reviewDto, MultipartFile imageFile, Long userKeyId) throws IOException {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!existingReview.getUser().getUserKeyId().equals(userKeyId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        // Process image file if not empty
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            reviewDto.setImageUrl(imageUrl);
        }

        // Update fields from DTO
        updateEntityFromDto(existingReview, reviewDto);
        existingReview = reviewRepository.save(existingReview);
        return convertToDto(existingReview);
    }

    // 리뷰 삭제
    public void deleteReview(Long id, Long userKeyId) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with ID " + id + " not found."));

        if (!review.getUser().getUserKeyId().equals(userKeyId)) {
            throw new AccessDeniedException("You do not have permission to delete this review.");
        }

        reviewRepository.deleteById(id);
    }

    private Review convertToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewText(reviewDto.getReviewText());
        review.setUseYn(reviewDto.getUseYn());
        return review;
    }

    // 지역 ID에 따라 리뷰를 조회하는 메소드
    public List<ReviewDto> getReviewsByRegion(Long regionId) {
        return reviewTransactionalScope(regionId);
    }

    private List<ReviewDto> reviewTransactionalScope(Long regionId){
        return reviewRepository.findByRegionId(regionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();

        dto.setReviewId(review.getReviewId());
        if (review.getUser() != null) {
            dto.setUserKeyId(review.getUser().getUserKeyId());
            dto.setUserNickName(review.getUser().getUserNickName()); // 사용자 닉네임 추가
        }
        // 기타 필드 설정
        dto.setReviewTitle(review.getReviewTitle());
        dto.setReviewText(review.getReviewText());
        dto.setDatePosted(review.getDatePosted());
        dto.setReviewDateModi(review.getReviewDateModi());
        dto.setDeletedTime(review.getDeletedTime());
        dto.setIsDeleted(review.isDeleted());
        dto.setUseYn(review.isUseYn());
        dto.setImageUrl(review.getImageUrl());
        dto.setRating(review.getRating());

        return dto;
    }

    private void updateEntityFromDto(Review existingReview, ReviewDto reviewDto) {

        existingReview.setReviewTitle(reviewDto.getReviewTitle());
        existingReview.setReviewText(reviewDto.getReviewText());
        existingReview.setUseYn(reviewDto.getUseYn());
        existingReview.setReviewDateModi(LocalDateTime.now());

        if (reviewDto.getDatePosted() != null) {
            existingReview.setDatePosted(reviewDto.getDatePosted());
        }
        if (reviewDto.getReviewDateModi() != null) {
            existingReview.setReviewDateModi(reviewDto.getReviewDateModi());
        }
        if (reviewDto.getDeletedTime() != null) {
            existingReview.setDeletedTime(reviewDto.getDeletedTime());
        }
        if (reviewDto.getIsDeleted() != null) {
            existingReview.setIsDeleted(reviewDto.getIsDeleted());
        }
        if (reviewDto.getImageUrl() != null) {
            existingReview.setImageUrl(reviewDto.getImageUrl());
        }

        // User 및 Region 엔티티 연결 (ID를 통해 조회 후 설정)
        if (reviewDto.getUserKeyId() != null) {
            User user = userRepository.findById(reviewDto.getUserKeyId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            existingReview.setUser(user);
        }
        if (reviewDto.getRegionId() != null) {
            Region region = regionRepository.findById(reviewDto.getRegionId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));
            existingReview.setRegion(region);
        }
        if (reviewDto.getSubRegionId() != null) {
            SubRegion subRegion = subRegionRepository.findById(reviewDto.getSubRegionId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subRegion ID"));
            existingReview.setSubRegion(subRegion);
        }
    }


    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }

    // 별점이 0.0에서 5.0 사이이며 0.5 단위로 입력되는지 확인
    private void validateRating(double rating) {
        if (rating < 0.0 || rating > 5.0 || (rating % 0.5 != 0)) {
            throw new IllegalArgumentException("잘못된 평점 : " + rating + ". 평점은 0.0 부터 5.0까지 0.5점 단위어야함.");
        }
    }

    public List<ReviewDto> getReviewsByRegionAndSubRegion(Long regionId, Long subRegionId) {
        List<Review> reviewsByRegion = reviewRepository.findByRegionId(regionId);
        List<Review> reviewsBySubRegion = reviewRepository.findBySubRegionId(subRegionId);

        // 두 조건에 따라 조회된 리뷰를 합치고, 중복을 제거
        return Stream.concat(reviewsByRegion.stream(), reviewsBySubRegion.stream())
                .distinct() // 중복 제거
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsBySubRegionId(Long subRegionId) {
        List<Review> reviews = reviewRepository.findBySubRegionId(subRegionId);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }
}
