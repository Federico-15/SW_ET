package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Images")
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long imageId;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;  // URL of the image

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;  // Server storage path of the image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = true, columnDefinition = "INT UNSIGNED")
    private Review review;  // Associate with Review, nullable if not every image is linked to a review

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;  // The user who uploaded the image
}