package com.lfokazi.service.statement.save.create;

import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.Statement;
import io.awspring.cloud.s3.S3Resource;
import jakarta.annotation.Nonnull;

public interface StatementCreator {
    @Nonnull Statement create(@Nonnull Customer customer, S3Resource s3Resource);
}
