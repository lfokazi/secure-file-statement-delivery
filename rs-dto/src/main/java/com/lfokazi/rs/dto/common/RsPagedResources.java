package com.lfokazi.rs.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Builder
@Jacksonized
@Getter
public class RsPagedResources<TDto> {
    private final RsPageInfo pageInfo;
    private final Collection<TDto> content;
}
