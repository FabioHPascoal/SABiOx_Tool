package br.com.sabiox.sabiox_tool.repositories.sabiox.phases.requirements.comment.commentRating;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUser;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRatingRepository extends JpaRepository<CommentRating, Long> {
    Optional<CommentRating> findByCommentIdAndUserId(Long commentId, Long userId);
}