package com.lfokazi.service.statement.download.load;

import com.lfokazi.BaseIntegrationTest;
import com.lfokazi.domain.statement.download.StatementDownload;
import com.lfokazi.dto.statement.download.filter.StatementDownloadFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementDownloadServiceTest extends BaseIntegrationTest {
    @Autowired
    private StatementDownloadService statementDownloadService;

    @Test
    void findStatements_no_filter() {
        // find all
        var pageable = Pageable.ofSize(20);
        var filter = StatementDownloadFilter.builder().build();

        var downloads = statementDownloadService.findDownloads(pageable, filter);
        assertThat(downloads.getNumberOfElements()).isEqualTo(11);
        assertThat(downloads.getTotalElements()).isEqualTo(11);
        assertThat(downloads.getSize()).isEqualTo(20);

        assertThat(downloads.getContent().stream().map(StatementDownload::getId))
                .containsExactlyInAnyOrder(4000L, 4001L, 4002L, 4003L, 4004L,
                        4005L, 4006L, 4007L, 4008L, 4009L, 4010L);
    }

    @Test
    void findStatements_by_customer_id() {
        // find all
        var pageable = Pageable.ofSize(20);
        var filter = StatementDownloadFilter.builder()
                .customerId(2002L)
                .build();

        var downloads = statementDownloadService.findDownloads(pageable, filter);
        assertThat(downloads.getNumberOfElements()).isEqualTo(5);
        assertThat(downloads.getTotalElements()).isEqualTo(5);
        assertThat(downloads.getSize()).isEqualTo(20);

        assertThat(downloads.getContent().stream().map(StatementDownload::getId))
                .containsExactlyInAnyOrder(4005L, 4006L, 4007L, 4008L, 4009L);
    }
}
