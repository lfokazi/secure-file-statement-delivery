package com.lfokazi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.S3Operations;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@WithMockUser(username = "auth0:68d3998b9344ced225d12a54", roles = {"WORKFORCE"})
@Testcontainers
@Transactional
public abstract class BaseIntegrationTest {
    @Value("${app.aws.s3.statements.bucket-name}")
    private String bucketName;
    @Autowired
    private S3Operations s3Operations;

    protected ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setup() {
        if (s3Operations.bucketExists(bucketName)) {
            return;
        }

        s3Operations.createBucket(bucketName);
        assertThat(s3Operations.bucketExists(bucketName)).isTrue();
    }

    protected MockMultipartFile getFile(String fileName, String filePath) throws IOException {
        var fileInputStream = getClass().getResourceAsStream(filePath);
        assertThat(fileInputStream).isNotNull();
        return new MockMultipartFile(fileName, filePath, MediaType.TEXT_HTML_VALUE, fileInputStream);
    }
}
