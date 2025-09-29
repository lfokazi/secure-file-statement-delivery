package com.lfokazi.service.statement.save.upload;

import com.lfokazi.core.exception.ObjectNotFoundException;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.upload.StatementUpload;
import com.lfokazi.repository.customer.CustomerRepository;
import com.lfokazi.service.statement.save.create.StatementCreator;
import io.awspring.cloud.s3.S3Operations;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StatementUploaderImpl implements StatementUploader {
    @Value("${app.aws.s3.statements.bucket-name}")
    private String bucketName;
    @Value("${app.aws.s3.statements.object-key-prefix}")
    private String bucketPrefix;

    private final CustomerRepository customerRepository;
    private final S3Operations s3Operations;
    private final StatementCreator statementCreator;

    @Timed
    @Override
    public Statement upload(@Nonnull StatementUpload statementUpload, @Nonnull MultipartFile file) throws IOException {
        var customer = customerRepository.findById(statementUpload.getCustomerId())
                .orElseThrow(() -> new ObjectNotFoundException("Customer not found."));

        var fileName = file.getOriginalFilename();
        var objectKey = String.format("%s%s/%s", bucketPrefix, customer.getId(), fileName);

        var s3Resource = s3Operations.upload(bucketName, objectKey, file.getInputStream());
        return statementCreator.create(customer, s3Resource);
    }
}
