package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRating;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingType;

import java.time.LocalDate;

public record CommentResponseDTO(
        Long id,
        Long userId,
        Long requirementId,
        LocalDate creationDate,
        Long likeCount,
        Long dislikeCount,
        String body,
        CommentRatingType userHasRated
) {
    public CommentResponseDTO(Comment comment, Long authUserId) {
        this(
                comment.getId(),
                comment.getUser().getId(),
                comment.getRequirement().getId(),
                comment.getCreationDate(),
                comment.getCommentRatings().stream()
                        .filter(c -> c.getCommentRatingType().equals(CommentRatingType.LIKE)).count(),
                comment.getCommentRatings().stream()
                        .filter(c -> c.getCommentRatingType().equals(CommentRatingType.DISLIKE)).count(),
                comment.getBody(),
                comment.getCommentRatings().stream()
                        .filter(c -> c.getUser().getId().equals(authUserId))
                        .map(CommentRating::getCommentRatingType)
                        .findFirst()
                        .orElse(null)
        );
    }
}
