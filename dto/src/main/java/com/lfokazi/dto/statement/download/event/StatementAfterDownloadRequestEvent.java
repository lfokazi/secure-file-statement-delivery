package com.lfokazi.dto.statement.download.event;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.net.URL;
import java.time.Duration;

@Builder
@Jacksonized
@Getter
public class StatementAfterDownloadRequestEvent {
    private final URL url;
    private final Duration duration;
    private final long statementId;
    private final String userRequestId;
}
