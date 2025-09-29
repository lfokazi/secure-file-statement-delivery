package com.lfokazi.dto.statement.download.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Builder
@Jacksonized
@Getter
public class StatementDownloadFilter {
    @Nullable
    @Positive
    @Schema(description = "Filter by customer ID", example = "2002", requiredMode = NOT_REQUIRED)
    private final Long customerId;
    @Nullable
    @Positive
    @Schema(description = "Filter by statement ID", example = "3005", requiredMode = NOT_REQUIRED)
    private final Long statementId;
    @Nullable
    @Schema(description = "Filter by user who requested download", example = "auth0:customer001", requiredMode = NOT_REQUIRED)
    private final String userRequestedId;
    @Nullable
    @Schema(description = "Filter downloads from this date", example = "2025-08-01T00:00:00", requiredMode = NOT_REQUIRED)
    private final LocalDateTime downloadedFrom;
    @Nullable
    @Schema(description = "Filter downloads to this date", example = "2025-08-31T23:59:59", requiredMode = NOT_REQUIRED)
    private final LocalDateTime downloadedTo;
}
