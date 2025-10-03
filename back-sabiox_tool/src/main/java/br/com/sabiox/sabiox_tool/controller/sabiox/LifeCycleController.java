package br.com.sabiox.sabiox_tool.controller.sabiox;

import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.LifeCycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class LifeCycleController {
    @Autowired
    LifeCycleService lifeCycleService;

//    @PostMapping("project")
//    public ResponseEntity<LifeCycleResponseDTO> createLifeCycle(@Valid @RequestBody LifeCycleRequestDTO lifeCycleRequestDTO,
//            @AuthenticationPrincipal User authUser) {
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(lifeCycleService.create(projectRequestDTO, authUser.getEmail()));
//    }
}
