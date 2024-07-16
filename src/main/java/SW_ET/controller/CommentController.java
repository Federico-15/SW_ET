package SW_ET.controller;

import SW_ET.dto.CommentDto;
import SW_ET.service.CommentService;
import SW_ET.config.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable Long reviewId, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);
            commentDto.setUserKeyId(userKeyId);
            commentDto.setReviewId(reviewId);

            CommentDto createdComment = commentService.createComment(commentDto);
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            log.error("Error occurred while creating comment: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating comment: " + e.getMessage());
        }
    }


    // 특정 리뷰의 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByReviewId(@PathVariable Long reviewId) {
        List<CommentDto> comments = commentService.getCommentsByReviewId(reviewId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 업데이트
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);
            commentDto.setUserKeyId(userKeyId);
            CommentDto updatedComment = commentService.updateComment(commentId, commentDto);
            return ResponseEntity.ok(updatedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(token);
            commentService.deleteComment(commentId, userKeyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
