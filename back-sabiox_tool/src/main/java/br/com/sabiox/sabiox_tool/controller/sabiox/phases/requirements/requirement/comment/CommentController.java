package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements.requirement.comment;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.commentRating.CommentRatingResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.requirement.comment.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/project/requirement/{requirementId}")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long requirementId,
            @Valid @RequestBody CommentRequestDTO request,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(requirementId, authUser.getId(), request));
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<CommentRatingResponseDTO> rateComment(
            @PathVariable Long requirementId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRatingRequestDTO request,
            @AuthenticationPrincipal User authUser) {

        CommentRatingResponseDTO response = commentService
                .rateComment(requirementId, commentId, authUser.getId(), request);

        if (response == null) return ResponseEntity.noContent().build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
