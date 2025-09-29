package com.lfokazi.service.statement.save.upload;

import com.lfokazi.core.exception.InternalErrorException;
import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.service.statement.save.create.StatementCreator;
import io.awspring.cloud.s3.S3Operations;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
class StatementUploadDelegate {
    @Value("${app.aws.s3.statements.bucket-name}")
    private String bucketName;
    @Value("${app.aws.s3.statements.object-key-prefix}")
    private String bucketPrefix;

    private final S3Operations s3Operations;
    private final StatementCreator statementCreator;

    @Timed
    Statement upload(@Nonnull Customer customer, @Nonnull MultipartFile file) {
        var nowMillis = System.currentTimeMillis();
        var objectKey = String.format("%s%s/%s", bucketPrefix, customer.getId(), nowMillis);

        try (var inputStream = file.getInputStream()) {
            var s3Resource = s3Operations.upload(bucketName, objectKey, inputStream);
            return statementCreator.create(customer, s3Resource);
        } catch (IOException e) {
            throw new InternalErrorException("Failed to read the uploaded file with error: " + e.getMessage(), e);
        }
    }
}
