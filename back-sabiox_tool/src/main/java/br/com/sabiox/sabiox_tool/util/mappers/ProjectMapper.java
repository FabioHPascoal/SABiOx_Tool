package br.com.sabiox.sabiox_tool.util.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.Project;
import br.com.sabiox.sabiox_tool.util.dtos.response.ProjectResponseDTO;

public class ProjectMapper {
    public static ProjectResponseDTO toDto(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getUser().getId(),
                project.getTitle(),
                project.getDescription()
        );
    }

    public static List<ProjectResponseDTO> toDtoList(List<Project> projects) {
        if (projects == null) {
            return Collections.emptyList();
        }

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toList());
    }
}