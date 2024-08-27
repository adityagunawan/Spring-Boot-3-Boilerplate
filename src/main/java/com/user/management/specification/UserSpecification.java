package com.user.management.specification;

import com.user.management.dto.ListUserFilterRequest;
import com.user.management.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification extends BaseSpecification {
    public static Specification<User> hasNameLike(ListUserFilterRequest filter) {
        return like("name", filter.getName());
    }

    public static Specification<User> hasEmailLike(ListUserFilterRequest filter) {
        return like("email", filter.getEmail());
    }

    public static Specification<User> hasUsernameLike(ListUserFilterRequest filter) {
        return like("username", filter.getUsername());
    }
}
