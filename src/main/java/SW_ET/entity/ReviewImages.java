package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ReviewImages")
@Getter
@Setter
public class ReviewImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long imageId;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;  // 이미지 URL

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;  // 서버 저장 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Review review;  // 리뷰와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;  // 이미지를 업로드한 사용자

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;  // 파일 이름

    @Column(name = "content_type", nullable = false, length = 255)
    private String contentType;  // 파일 타입

    @Column(name = "file_size")
    private Long fileSize;  // 파일 크기

}
