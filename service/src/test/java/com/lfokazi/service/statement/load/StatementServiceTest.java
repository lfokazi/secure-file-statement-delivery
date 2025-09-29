package com.lfokazi.service.statement.load;

import com.lfokazi.BaseIntegrationTest;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.filter.StatementFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementServiceTest extends BaseIntegrationTest {
    @Autowired
    private StatementService statementService;

    @Test
    void findStatements_no_filter() {
        // find all
        var pageable = Pageable.ofSize(20);
        var filter = StatementFilter.builder().build();

        var statements = statementService.findStatements(pageable, filter);
        assertThat(statements.getNumberOfElements()).isEqualTo(10);
        assertThat(statements.getTotalElements()).isEqualTo(10);
        assertThat(statements.getSize()).isEqualTo(20);

        assertThat(statements.getContent().stream().map(Statement::getId))
                .containsExactlyInAnyOrder(3000L, 3001L, 3002L, 3003L, 3004L,
                        3005L, 3006L, 3007L, 3008L, 3009L);
    }

    @Test
    void findStatements_by_customer_id() {
        // find all
        var pageable = Pageable.ofSize(20);
        var filter = StatementFilter.builder()
                .customerId(2002L)
                .build();

        var statements = statementService.findStatements(pageable, filter);
        assertThat(statements.getNumberOfElements()).isEqualTo(4);
        assertThat(statements.getTotalElements()).isEqualTo(4);
        assertThat(statements.getSize()).isEqualTo(20);

        assertThat(statements.getContent().stream().map(Statement::getId))
                .containsExactlyInAnyOrder(3005L, 3006L, 3007L, 3008L);
    }
}
