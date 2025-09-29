package com.lfokazi.service.statement.download.eventListener;

import com.lfokazi.dto.statement.download.event.StatementAfterDownloadRequestEvent;
import com.lfokazi.service.statement.download.save.create.StatementDownloadCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatementAfterDownloadRequestEventListener {
    private final StatementDownloadCreator statementDownloadCreator;

    @EventListener
    public void handleEventFromTests(StatementAfterDownloadRequestEvent event) {
        statementDownloadCreator.create(event);
    }
}
