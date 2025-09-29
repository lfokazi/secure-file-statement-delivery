package com.lfokazi.service.statement.download.save.create;

import com.lfokazi.dto.statement.download.event.StatementAfterDownloadRequestEvent;
import jakarta.annotation.Nonnull;
import org.springframework.scheduling.annotation.Async;

public interface StatementDownloadCreator {
    @Async
    void create(@Nonnull StatementAfterDownloadRequestEvent downloadEvent);
}
