package com.lfokazi.service.statement.save.download;

import com.lfokazi.BaseIntegrationTest;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.download.StatementDownloadParams;
import com.lfokazi.service.statement.save.upload.StatementUploader;
import io.awspring.cloud.s3.S3Operations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementDownloaderTest extends BaseIntegrationTest {
    private static final String TEST_FILE_NAME = "monthly_statement.html";
    private static final String TEST_FILE_PATH = "/statements/" + TEST_FILE_NAME;

    @Autowired
    private S3Operations s3Operations;
    @Autowired
    private StatementUploader statementUploader;
    @Autowired
    private StatementDownloader statementDownloader;

    @Test
    void download_success() throws IOException {
        // upload a statement first
        var customerId = 2002L;
        var uploadedStatement = uploadStatement(customerId);
        assertThat(uploadedStatement).isNotNull();

        // request download
        var downloadParams = StatementDownloadParams.builder()
                .durationInMillis(Duration.ofDays(3).toMillis())
                .build();

        var downloadInfo = statementDownloader.download(uploadedStatement.getId(), downloadParams);
        assertThat(downloadInfo).isNotNull();
        assertThat(downloadInfo.getUrl()).isNotNull();
        assertThat(downloadInfo.getDuration()).isEqualTo(Duration.ofDays(3));

        var newDownload = uploadedStatement.getDownloads().stream().findFirst();
        assertThat(newDownload).isPresent();
        assertThat(newDownload.get().getUrl()).isEqualTo(downloadInfo.getUrl().toString());
    }

    private Statement uploadStatement(long customerId) throws IOException {
        var file = getFile(TEST_FILE_NAME, TEST_FILE_PATH);

        var uploadedStatement = statementUploader.upload(customerId, file);
        assertThat(uploadedStatement).isNotNull();
        var existsInS3 = s3Operations.objectExists(uploadedStatement.getBucketName(), uploadedStatement.getObjectKey());
        assertThat(existsInS3).isTrue();

        return uploadedStatement;
    }
}
