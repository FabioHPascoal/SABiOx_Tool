package br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.requirement.comment;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.Requirement;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.Comment;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRating;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.phases.requirements.RequirementRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.phases.requirements.comment.CommentRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.phases.requirements.comment.commentRating.CommentRatingRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {
    @Autowired
    RequirementRepository requirementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    CommentRatingRepository commentRatingRepository;

    private Requirement checkUserProject(Long requirementId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requirement not found."));

        Project project = requirement.getElicitRequirements().getLifeCycle().getPhase().getProject();
        projectAuthorizationService.assertMember(project.getId(), userId);
        return requirement;
    }

    @Transactional
    public CommentResponseDTO createComment(
            Long requirementId, Long userId, CommentRequestDTO request) {

        Requirement requirement = checkUserProject(requirementId, userId);

        Comment comment = new Comment();
        comment.setUser(userRepository.getReferenceById(userId));
        comment.setRequirement(requirement);
        comment.setBody(request.body());

        return new CommentResponseDTO(commentRepository.save(comment), userId);
    }

    @Transactional
    public CommentRatingResponseDTO rateComment(
            Long requirementId, Long commentId, Long userId, CommentRatingRequestDTO request) {

        checkUserProject(requirementId, userId);

        CommentRating commentRating = commentRatingRepository
                .findByCommentIdAndUserId(commentId, userId)
                .orElse(null);

        if (commentRating != null &&
                request.commentRatingType().equals(commentRating.getCommentRatingType())) {
            commentRatingRepository.delete(commentRating);
            return null;
        }

        if (commentRating == null) {
            commentRating = new CommentRating();
            commentRating.setComment(commentRepository.getReferenceById(commentId));
            commentRating.setUser(userRepository.getReferenceById(userId));
        }

        commentRating.setCommentRatingType(request.commentRatingType());
        return new CommentRatingResponseDTO(commentRatingRepository.save(commentRating));
    }
}
