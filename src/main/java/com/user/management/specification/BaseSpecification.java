package com.user.management.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseSpecification {

    public static <T> Specification<T> like(String field, String value) {
        if (!Optional.ofNullable(value).orElse("").isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        }

        return null;
    }

    public static <T> Specification<T> equal(String field, Object value) {
        if (Objects.nonNull(value)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value);
        }

        return null;
    }

    public static <T> Specification<T> notEqual(String field, Object value) {
        if (Objects.nonNull(value)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(field), value);
        }

        return null;
    }

    public static <T> Specification<T> isIn(String field, List<?> value) {
        if (Objects.nonNull(value)) {
            return (root, query, criteriaBuilder) -> root.get(field).in(value);
        }

        return null;
    }

    public static <T> Specification<T> notIn(String field, List<?> value) {
        if (Objects.nonNull(value)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.not(root.get(field).in(value));
        }

        return null;
    }

    public static <T> Specification<T> notNull(String field) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field));
    }

    public static <T> Specification<T> isNull(String field) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(field));
    }

    public static<T> Specification<T> distinct(String columnName) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get(columnName)));
            return null;
        };
    }

    public static<T> Specification<T> betweent(String field, Date dateBefore, Date dateAfter) {
        if (Objects.nonNull(dateBefore) && Objects.nonNull(dateAfter)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(field), dateBefore, dateAfter);
        }

        return null;
    }
}
