package com.lfokazi.specification.statement.download;

import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.dto.statement.download.filter.StatementDownloadFilter;
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
public class StatementDownloadSpecification implements Specification<StatementDownload> {
    private final StatementDownloadFilter filter;

    @Override
    public Predicate toPredicate(@Nonnull Root<StatementDownload> root, CriteriaQuery<?> query,
                                 @Nonnull CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();
        ofNullable(filter.getStatementId()).ifPresent(it ->
                predicates.add(criteriaBuilder.equal(root.get("statement").get("id"), it)));
        ofNullable(filter.getCustomerId()).ifPresent(it ->
                predicates.add(criteriaBuilder.equal(root.get("statement").get("customer").get("id"), it)));
        ofNullable(filter.getUserRequestedId()).ifPresent(it ->
                predicates.add(criteriaBuilder.equal(root.get("userRequestedId"), it)));
        ofNullable(filter.getDownloadedFrom()).ifPresent(it ->
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("created"), it)));
        ofNullable(filter.getDownloadedTo()).ifPresent(it ->
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("created"), it)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
