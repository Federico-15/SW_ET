package SW_ET.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageDto {
    private String imageUrl;
    private String imagePath;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Long reviewId;
}
