package com.lfokazi.service.statement.save.download;

import com.lfokazi.core.exception.ObjectNotFoundException;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.download.StatementDownloadParams;
import com.lfokazi.dto.statement.download.info.StatementDownloadInfo;
import com.lfokazi.repository.statement.StatementRepository;
import com.lfokazi.dto.statement.download.event.StatementAfterDownloadRequestEvent;
import io.awspring.cloud.s3.S3Operations;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class StatementDownloaderImpl implements StatementDownloader {
    private final StatementRepository statementRepository;
    private final S3Operations s3Operations;
    private final ApplicationEventPublisher eventPublisher;

    @Nonnull
    @Override
    public StatementDownloadInfo download(@Nonnull StatementDownloadParams params) {
        var statement = statementRepository.findById(params.getStatementId())
                .orElseThrow(() -> new ObjectNotFoundException("Statement not found."));
        var duration = Duration.ofMillis(params.getDurationInMillis());

        var url = s3Operations.createSignedGetURL(statement.getBucketName(), statement.getObjectKey(), duration);

        publishAfterDownloadRequestEvent(url, duration, statement);

        return StatementDownloadInfo.builder()
                .url(url)
                .duration(duration)
                .build();
    }

    private void publishAfterDownloadRequestEvent(URL url, Duration duration, Statement statement) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var event = StatementAfterDownloadRequestEvent.builder()
                .userRequestId(userId)
                .url(url)
                .duration(duration)
                .statementId(statement.getId()).build();

        eventPublisher.publishEvent(event);
    }
}
