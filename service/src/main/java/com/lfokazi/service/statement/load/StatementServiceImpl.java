package com.lfokazi.service.statement.load;

import com.lfokazi.domain.statement.Statement;
import com.lfokazi.dto.statement.filter.StatementFilter;
import com.lfokazi.repository.statement.StatementRepository;
import com.lfokazi.specification.statement.StatementSpecification;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementRepository statementRepository;

    @Nonnull
    @Override
    public Page<Statement> findStatements(@Nonnull Pageable pageable, @Nonnull StatementFilter filter) {
        var spec = StatementSpecification.builder()
                .filter(filter).build();

        return statementRepository.findAll(spec, pageable);
    }
}
