package br.com.sabiox.sabiox_tool.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {
    public static <T> Specification<T> filterIgnoringAccentAndCase(String fieldPath, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String[] pathParts = fieldPath.split("\\.");
            Path<String> field = root.get(pathParts[0]);

            for (int i = 1; i < pathParts.length; i++) {
                field = field.get(pathParts[i]);
            }

            var fieldExpression = applyUnaccentAndLower(criteriaBuilder, field);
            var searchValue = applyUnaccentAndLower(criteriaBuilder, value);

            return criteriaBuilder.like(fieldExpression, searchValue);
        };
    }

    private static <T> Expression<String> applyUnaccentAndLower(CriteriaBuilder criteriaBuilder, Path<String> field) {
        return criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(field));
    }

    private static Expression<String> applyUnaccentAndLower(CriteriaBuilder criteriaBuilder, String value) {
        return criteriaBuilder.function("unaccent", String.class, criteriaBuilder.literal("%" + value.toLowerCase() + "%"));
    }
}
