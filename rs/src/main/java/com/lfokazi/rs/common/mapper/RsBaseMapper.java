package com.lfokazi.rs.common.mapper;

import com.lfokazi.rs.dto.common.RsPageInfo;
import com.lfokazi.rs.dto.common.RsPagedResources;
import org.springframework.data.domain.Page;

public abstract class RsBaseMapper<TEntity, TDto> {
    public abstract TDto map(TEntity entity);

    public RsPagedResources<TDto> mapPage(Page<TEntity> page) {
        return RsPagedResources.<TDto>builder()
                .pageInfo(mapPageInfo(page))
                .content(page.map(this::map).getContent())
                .build();
    }

    private RsPageInfo mapPageInfo(Page<TEntity> page) {
        return RsPageInfo.builder()
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber())
                .build();
    }
}
