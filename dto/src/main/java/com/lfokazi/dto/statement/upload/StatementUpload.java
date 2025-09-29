package com.lfokazi.dto.statement.upload;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class StatementUpload {
    @Positive
    private final long customerId;
}
