package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating;

public record CommentRatingResponseDTO(
        Long id,
        Long userId,
        Long commentId,
        CommentRatingType commentRatingType
) {
    public CommentRatingResponseDTO(CommentRating commentRating) {
        this(
                commentRating.getId(),
                commentRating.getUser().getId(),
                commentRating.getComment().getId(),
                commentRating.getCommentRatingType()
        );
    }
}
