package com.lfokazi.service.statement.download.load;

import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.dto.statement.download.filter.StatementDownloadFilter;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StatementDownloadService {
    @Nonnull Page<StatementDownload> findDownloads(@Nonnull Pageable pageable, @Nonnull StatementDownloadFilter filter);
    @Nonnull Page<StatementDownload> findDownloads(@Nonnull Pageable pageable, long statementId,
                                                   @Nonnull StatementDownloadFilter filter);
}
