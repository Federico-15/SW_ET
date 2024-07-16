package SW_ET.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingDto {
    private Long itemId;
    private String itemType;
    private Double ratingValue;
    private String comment;
    private Long userId;
}

