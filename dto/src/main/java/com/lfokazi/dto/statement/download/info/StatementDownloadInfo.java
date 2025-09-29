package com.lfokazi.dto.statement.download.info;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Jacksonized
@Getter
public class StatementDownloadInfo {
    private final URL url;
    private final Duration duration;
    private final LocalDateTime expiresAt;
}
