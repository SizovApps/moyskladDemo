package com.romashko.romashkoTestProject.services.utils;

import com.romashko.romashkoTestProject.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> columnEqual(String columnName, Object columnValue) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(columnName), columnValue);
            }
        };
    }


    public static Specification<Product> columnContains(String columnName, Object columnValue) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(columnName), "%" + columnValue + "%");
            }
        };
    }

    public static Specification<Product> columnGreaterThan(String columnName, Float columnValue) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThan(root.get(columnName), columnValue);
            }
        };
    }

    public static Specification<Product> columnLowerThan(String columnName, Float columnValue) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThan(root.get(columnName), columnValue);
            }
        };
    }

    public static Specification<Product> isAvailable(String columnName, Boolean columnValue) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(columnName), columnValue);
            }
        };
    }
}

