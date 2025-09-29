package com.lfokazi.service.statement.save.upload;

import com.lfokazi.domain.statement.Statement;
import jakarta.annotation.Nonnull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

public interface StatementUploader {
    Statement upload(long customerId, @Nonnull MultipartFile file);
    @Async
    void uploadAsync(long customerId, @Nonnull MultipartFile file);
}
