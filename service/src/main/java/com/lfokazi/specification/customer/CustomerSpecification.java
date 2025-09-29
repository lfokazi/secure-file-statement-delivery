package com.lfokazi.specification.customer;

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

@Builder
@RequiredArgsConstructor
public class CustomerSpecification implements Specification<Statement> {
    private final StatementFilter filter;

    @Override
    public Predicate toPredicate(@Nonnull Root<Statement> root, CriteriaQuery<?> query,
                                 @Nonnull CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
