package com.lfokazi.rs.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class RsPageInfo {
    private final long size;
    private final long totalElements;
    private final long totalPages;
    private final long number;
}
