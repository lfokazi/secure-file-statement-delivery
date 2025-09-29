package com.lfokazi.dto.statement.download;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.Duration;

@Builder
@Jacksonized
@Getter
public class StatementDownloadParams {
    @Positive
    @Builder.Default
    private final long durationInMillis = Duration.ofDays(7).toMillis();
}
