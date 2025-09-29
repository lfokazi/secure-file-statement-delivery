package com.lfokazi.rs.mapper.document.customer.statement;

import com.lfokazi.domain.statement.Statement;
import com.lfokazi.rs.common.mapper.RsBaseMapper;
import com.lfokazi.rs.dto.statement.RsStatement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RsStatementMapper extends RsBaseMapper<Statement, RsStatement> {
    private final ModelMapper mapper;

    @Override
    public RsStatement map(Statement rsStatement) {
        var dto = mapper.map(rsStatement, RsStatement.class);
        dto.setCreatedAt(rsStatement.getCreated());

        var customer = rsStatement.getCustomer();
        dto.setCustomerFirstName(customer.getFirstName());
        dto.setCustomerLastName(customer.getLastName());

        return dto;
    }
}
