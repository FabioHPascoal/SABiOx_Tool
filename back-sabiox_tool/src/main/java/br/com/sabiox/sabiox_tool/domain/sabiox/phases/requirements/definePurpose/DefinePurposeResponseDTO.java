package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose;

public record DefinePurposeResponseDTO(
        Long id,
        String whatQuestion,
        String forWhatQuestion,
        String whyQuestion
) {
    public DefinePurposeResponseDTO(DefinePurpose definePurpose) {
        this(
                definePurpose.getId(),
                definePurpose.getWhatQuestion(),
                definePurpose.getForWhatQuestion(),
                definePurpose.getWhyQuestion()
        );
    }
}
