package com.lfokazi.service.statement.download.save.create;

import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.repository.statement.StatementRepository;
import com.lfokazi.repository.statement.download.StatementDownloadRepository;
import com.lfokazi.dto.statement.download.event.StatementAfterDownloadRequestEvent;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementDownloadCreatorImpl implements StatementDownloadCreator {
    private final StatementDownloadRepository statementDownloadRepository;
    private final StatementRepository statementRepository;

    @Async
    @WithSpan
    @Override
    public void create(@Nonnull StatementAfterDownloadRequestEvent downloadEvent) {
        var statement = statementRepository.findById(downloadEvent.getStatementId());
        if (statement.isEmpty()) {
            log.warn("Statement with id {} not found. Unable to record download request",
                    downloadEvent.getStatementId());
            return;
        }

        var download = StatementDownload.builder()
                .url(downloadEvent.getUrl().toString())
                .userRequestedId(downloadEvent.getUserRequestId())
                .statement(statement.get())
                .durationMs(downloadEvent.getDuration().toMillis())
                .build();
        statement.get().getDownloads().add(download);

        statementDownloadRepository.save(download);
    }
}
