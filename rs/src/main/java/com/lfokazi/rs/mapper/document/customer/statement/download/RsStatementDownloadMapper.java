package com.lfokazi.rs.mapper.document.customer.statement.download;

import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.rs.common.mapper.RsBaseMapper;
import com.lfokazi.rs.dto.statement.download.RsStatementDownload;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class RsStatementDownloadMapper extends RsBaseMapper<StatementDownload, RsStatementDownload> {
    private final ModelMapper mapper;

    @Override
    public RsStatementDownload map(StatementDownload download) {
        var dto = mapper.map(download, RsStatementDownload.class);
        dto.setStatementId(download.getId());
        dto.setDownloadUrl(download.getUrl());
        dto.setExpiresAt(download.getDateRequested().plus(download.getDurationMs(), ChronoUnit.MILLIS));

        return dto;
    }
}
