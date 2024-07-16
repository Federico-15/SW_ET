package SW_ET.dto;

import SW_ET.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long userKeyId;
    private String reviewTitle;
    private String reviewText;
    private String userNickName;  // 사용자 닉네임 필드
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime datePosted;
    private LocalDateTime reviewDateModi;
    private Boolean isDeleted;
    private LocalDateTime deletedTime;
    private Boolean useYn = false;
    private Long regionId;
    private Long subRegionId;
    private String imageUrl;
    private double rating;

    public ReviewDto(Review review) {
        this.reviewId = review.getReviewId();
        this.userKeyId = review.getUser().getUserKeyId(); // 가정: Review 엔티티에 User 엔티티가 있고, User 엔티티에 getUserKeyId() 메소드가 있음
        this.reviewTitle = review.getReviewTitle();
        this.reviewText = review.getReviewText();
        this.datePosted = review.getDatePosted();
        this.reviewDateModi = review.getReviewDateModi();
        //this.isDeleted = review.getIsDeleted();
        this.deletedTime = review.getDeletedTime();
        //this.useYn = review.getUseYn();
        this.regionId = review.getRegion().getRegionId();
        this.subRegionId = review.getSubRegion().getSubRegionId();
        this.imageUrl = review.getImageUrl();
        this.rating = review.getRating();
        this.userNickName = review.getUser().getUserNickName();
    }
}
