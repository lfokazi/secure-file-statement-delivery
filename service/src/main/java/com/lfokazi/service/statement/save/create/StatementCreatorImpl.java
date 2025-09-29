package com.lfokazi.service.statement.save.create;

import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.repository.statement.StatementRepository;
import io.awspring.cloud.s3.S3Resource;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatementCreatorImpl implements StatementCreator {
    private final StatementRepository statementRepository;

    @Nonnull
    @Override
    public Statement create(@Nonnull Customer customer, S3Resource s3Resource) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var location = s3Resource.getLocation();
        var statement = Statement.builder()
                .userCreatedId(userId)
                .customer(customer)
                .bucketName(location.getBucket())
                .objectKey(location.getObject())
                .build();


        return statementRepository.save(statement);
    }
}
