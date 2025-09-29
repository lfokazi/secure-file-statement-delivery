package com.lfokazi.specification.statement;

import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.filter.StatementFilter;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Builder
@RequiredArgsConstructor
public class StatementSpecification implements Specification<Statement> {
    private final StatementFilter filter;

    @Override
    public Predicate toPredicate(@Nonnull Root<Statement> root, CriteriaQuery<?> query,
                                 @Nonnull CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();
        ofNullable(filter.getCustomerId()).ifPresent(it ->
                predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), it)));
        ofNullable(filter.getUserCreatedId()).ifPresent(it ->
                predicates.add(criteriaBuilder.equal(root.get("userCreatedId"), it)));
        ofNullable(filter.getFromDate()).ifPresent(it ->
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("created"), it)));
        ofNullable(filter.getToDate()).ifPresent(it ->
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("created"), it)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
