package com.lfokazi.service.statement.load;

import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.filter.StatementFilter;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StatementService {
    @Nonnull Page<Statement> findStatements(@Nonnull Pageable pageable, @Nonnull StatementFilter filter);
}
