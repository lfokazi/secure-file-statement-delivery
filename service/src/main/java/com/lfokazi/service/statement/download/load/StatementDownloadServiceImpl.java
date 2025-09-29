package com.lfokazi.service.statement.download.load;

import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.dto.statement.download.filter.StatementDownloadFilter;
import com.lfokazi.repository.statement.download.StatementDownloadRepository;
import com.lfokazi.specification.statement.download.StatementDownloadSpecification;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatementDownloadServiceImpl implements StatementDownloadService {
    private final StatementDownloadRepository downloadRepository;

    @Nonnull
    @Override
    public Page<StatementDownload> findDownloads(@Nonnull Pageable pageable, @Nonnull StatementDownloadFilter filter) {
        return findByFilter(pageable, null, filter);
    }

    @Nonnull
    @Override
    public Page<StatementDownload> findDownloads(@Nonnull Pageable pageable, long statementId,
                                                 @Nonnull StatementDownloadFilter filter) {
        return findByFilter(pageable, statementId, filter);
    }

    private Page<StatementDownload> findByFilter(@Nonnull Pageable pageable, @Nullable Long statementId,
                                                 @Nonnull StatementDownloadFilter filter) {
        var spec = StatementDownloadSpecification.builder()
                .statementId(statementId)
                .filter(filter).build();

        return downloadRepository.findAll(spec, pageable);
    }
}
