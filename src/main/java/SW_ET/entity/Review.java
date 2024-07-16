package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key_data_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;

    @Column(name = "review_title", nullable = false, length = 255)
    private String reviewTitle;

    @Lob
    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(name = "date_posted", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime datePosted;

    @Column(name = "review_date_modi", nullable = true)
    private LocalDateTime reviewDateModi;

    @Column(name = "use_yn", nullable = false, length = 1)
    private boolean useYn;

    @Column(name = "deleted_time", nullable = true)
    private LocalDateTime deletedTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_region_id")
    private SubRegion subRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private TravelDestination destination;

    // 이미지 URL을 저장하는 새로운 필드 추가
    @Column(name = "image_url", nullable = true, length = 2048) // URL 길이는 필요에 따라 조정 가능
    private String imageUrl;

    @Column(name = "rating", nullable = false)
    private double rating;  // 별점을 저장할 필드

    @PrePersist
    public void prePersist() {
        if (datePosted == null) {
            datePosted = LocalDateTime.now();
        }
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
