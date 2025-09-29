package com.lfokazi.dto.statement.filter;

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
public class StatementFilter {
    @Nullable
    @Positive
    @Schema(description = "Filter by customer ID", example = "2002", requiredMode = NOT_REQUIRED)
    private final Long customerId;
    @Nullable
    @Schema(description = "Filter by user who created the statement", example = "auth0:admin001", requiredMode = NOT_REQUIRED)
    private final String userCreatedId;
    @Nullable
    @Schema(description = "Filter statements from this date", example = "2025-08-01T00:00:00", requiredMode = NOT_REQUIRED)
    private LocalDateTime fromDate;
    @Nullable
    @Schema(description = "Filter statements to this date", example = "2025-08-31T23:59:59", requiredMode = NOT_REQUIRED)
    private LocalDateTime toDate;
}
