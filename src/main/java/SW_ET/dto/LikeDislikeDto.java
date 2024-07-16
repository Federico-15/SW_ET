package SW_ET.dto;

import SW_ET.entity.User;
import SW_ET.entity.types.UserRole;
import lombok.Data;

@Data
public class LikeDislikeDto {

    private Long userId; // 사용자 ID
    private Long reviewId;
    private boolean like; // true는 좋아요, false는 싫어요

    public LikeDislikeDto() {}

    public LikeDislikeDto(Long userId, Long reviewId, boolean like) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.like = like;
    }

}
