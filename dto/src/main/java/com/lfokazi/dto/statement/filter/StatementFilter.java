package com.lfokazi.dto.statement.filter;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
@Getter
public class StatementFilter {
    @Positive
    private final Long customerId;
    private final String userCreatedId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
