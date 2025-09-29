package com.lfokazi.service.statement.download.eventListener;

import com.lfokazi.dto.statement.download.event.StatementAfterDownloadRequestEvent;
import com.lfokazi.service.statement.download.save.create.StatementDownloadCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StatementAfterDownloadRequestEventListener {
    private final StatementDownloadCreator statementDownloadCreator;

    @TransactionalEventListener(condition = "!@environment.acceptsProfiles('test')")
    public void handleEvent(StatementAfterDownloadRequestEvent event) {
        statementDownloadCreator.create(event);
    }

    @EventListener(condition = "@environment.acceptsProfiles('test')")
    public void handleEventFromTests(StatementAfterDownloadRequestEvent event) {
        statementDownloadCreator.create(event);
    }
}
