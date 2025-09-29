package com.lfokazi.service.statement.save.download;

import com.lfokazi.dto.statement.download.StatementDownloadParams;
import com.lfokazi.dto.statement.download.info.StatementDownloadInfo;
import jakarta.annotation.Nonnull;

public interface StatementDownloader {
    @Nonnull
    StatementDownloadInfo download(@Nonnull StatementDownloadParams params);
}
