package SW_ET.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {
    private Long commentId;
    private Long userKeyId;
    private String username;
    private Long reviewId;
    private String commentText;
    private LocalDateTime commentDate;
    private Long parentCommentId;  // 대댓글의 경우 부모 댓글 ID
    private List<CommentDto> replies;  // 대댓글 목록

    // getters and setters 생략
}
