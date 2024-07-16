package SW_ET.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long imageId;
    private String imageUrl;
    private String imagePath;
    private Long reviewId; // 관련 리뷰 ID, 리뷰에 연결되지 않은 경우 null 허용
    private Long userId; // 이미지를 업로드한 사용자 ID

    // 필요에 따라 생성자 및 기타 메소드 추가
}