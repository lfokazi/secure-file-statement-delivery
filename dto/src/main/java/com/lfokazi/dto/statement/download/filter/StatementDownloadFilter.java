package com.lfokazi.dto.statement.download.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
@Getter
public class StatementDownloadFilter {
    private final Long customerId;
    private final Long statementId;
    private final String userRequestedId;
    private final LocalDateTime downloadedFrom;
    private final LocalDateTime downloadedTo;
}
