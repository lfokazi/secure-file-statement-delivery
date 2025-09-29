package com.lfokazi.dto.statement.download;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.Duration;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Builder
@Jacksonized
@Getter
public class StatementDownloadParams {
    @Positive
    @Builder.Default
    @Schema(
        description = "Duration in milliseconds for which the presigned URL will be valid",
        example = "259200000",
        requiredMode = NOT_REQUIRED,
        defaultValue = "604800000"
    ) // 7 days default, 3 days example
    private final long durationInMillis = Duration.ofDays(7).toMillis();
}
