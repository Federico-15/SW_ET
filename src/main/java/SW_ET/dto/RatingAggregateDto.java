package SW_ET.dto;

import lombok.Data;

@Data
public class RatingAggregateDto {
    private Long itemId;
    private String itemType;
    private Double averageRating;
}
