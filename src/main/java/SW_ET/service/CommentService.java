package SW_ET.service;

import SW_ET.dto.CommentDto;
import SW_ET.entity.Comment;
import SW_ET.entity.Review;
import SW_ET.entity.User;
import SW_ET.repository.CommentRepository;
import SW_ET.repository.ReviewRepository;
import SW_ET.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public CommentDto createComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserKeyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Review review = reviewRepository.findById(commentDto.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setReview(review);
        comment.setCommentText(commentDto.getCommentText());

        if (commentDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));
            comment.setParentComment(parentComment);
        }

        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    public List<CommentDto> getCommentsByReviewId(Long reviewId) {
        return commentRepository.findByReviewReviewId(reviewId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        existingComment.setCommentText(commentDto.getCommentText());
        existingComment = commentRepository.save(existingComment);

        return convertToDto(existingComment);
    }

    public void deleteComment(Long id, Long userKeyId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getUserKeyId().equals(userKeyId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        commentRepository.deleteById(id);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setUserKeyId(comment.getUser().getUserKeyId());
        commentDto.setUsername(comment.getUser().getUserNickName());
        commentDto.setReviewId(comment.getReview().getReviewId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setCommentDate(comment.getCommentDate());

        if (comment.getParentComment() != null) {
            commentDto.setParentCommentId(comment.getParentComment().getCommentId());
        }

        commentDto.setReplies(comment.getReplies() != null ?
                comment.getReplies().stream().map(this::convertToDto).collect(Collectors.toList()) :
                new ArrayList<>());

        return commentDto;
    }
}
