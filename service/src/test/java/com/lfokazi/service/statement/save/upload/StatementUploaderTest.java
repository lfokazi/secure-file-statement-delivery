package com.lfokazi.service.statement.save.upload;

import com.lfokazi.BaseIntegrationTest;
import com.lfokazi.core.exception.ObjectNotFoundException;
import io.awspring.cloud.s3.S3Operations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatementUploaderTest extends BaseIntegrationTest {
    private static final String TEST_FILE_NAME = "monthly_statement.html";
    private static final String TEST_FILE_PATH = "/statements/" + TEST_FILE_NAME;

    @Value("${app.aws.s3.statements.bucket-name}")
    private String bucketName;
    @Autowired
    private S3Operations s3Operations;
    @Autowired
    private StatementUploader statementUploader;

    @Test
    void upload_success() throws IOException {
        var customerId = 2002L;
        var file = getFile(TEST_FILE_NAME, TEST_FILE_PATH);

        var statement = statementUploader.upload(customerId, file);
        assertThat(statement).isNotNull();
        assertThat(statement.getId()).isNotNull();
        assertThat(statement.getBucketName()).isEqualTo(bucketName);
        assertThat(statement.getObjectKey()).contains("statements/2002/");
        assertThat(statement.getDownloads()).isEmpty();

        var existsInS3 = s3Operations.objectExists(statement.getBucketName(), statement.getObjectKey());
        assertThat(existsInS3).isTrue();
    }

    @Test
    void upload_unknown_customer() throws IOException {
        var unknownCustomerId = 9999L;
        var file = getFile(TEST_FILE_NAME, TEST_FILE_PATH);

        var ex = assertThrows(ObjectNotFoundException.class,
                () -> statementUploader.upload(unknownCustomerId, file));

        assertThat(ex.getMessage()).contains("Customer not found");
    }
}
