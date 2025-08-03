package br.com.sabiox.sabiox_tool.specification;

import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.domain.user.UserRole;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasName(String name) {
        return SpecificationUtils.filterIgnoringAccentAndCase("name", name);
    }

    public static Specification<User> hasEmail(String email) {
        return SpecificationUtils.filterIgnoringAccentAndCase("email", email);
    }

    public static Specification<User> hasRole(UserRole role) {
        return (root, query, criteriaBuilder) ->
                role == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("role"), role);
    }
}
