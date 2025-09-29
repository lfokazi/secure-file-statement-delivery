package com.lfokazi.service.statement.save.upload;

import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.upload.StatementUpload;
import jakarta.annotation.Nonnull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StatementUploader {
    Statement upload(@Nonnull StatementUpload statementUpload, @Nonnull MultipartFile file) throws IOException;
}
