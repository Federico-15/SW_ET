package SW_ET.repository;


import SW_ET.entity.Comment;
import SW_ET.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewAndParentCommentIsNull(Review review); // 리뷰에 대한 루트 댓글 조회
    List<Comment> findByParentComment(Comment parentComment); // 대댓글 조회
    List<Comment> findByReviewReviewId(Long reviewId);
}