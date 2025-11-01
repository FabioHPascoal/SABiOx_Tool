package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingType;
import br.com.sabiox.sabiox_tool.domain.user.UserReducedDTO;

import java.time.LocalDate;

public record CommentDTO(
        Long id,
        LocalDate creationDate,
        Long likeCount,
        Long dislikeCount,
        String body,
        UserReducedDTO user
) {
    public CommentDTO(Comment comment) {
        this(
                comment.getId(),
                comment.getCreationDate(),
                comment.getCommentRatings().stream()
                        .filter(c -> c.getCommentRatingType().equals(CommentRatingType.LIKE)).count(),
                comment.getCommentRatings().stream()
                        .filter(c -> c.getCommentRatingType().equals(CommentRatingType.DISLIKE)).count(),
                comment.getBody(),
                new UserReducedDTO(comment.getUser())
        );
    }
}
