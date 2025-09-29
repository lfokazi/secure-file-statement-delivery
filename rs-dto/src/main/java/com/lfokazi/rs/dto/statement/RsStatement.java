package com.lfokazi.rs.dto.statement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class RsStatement {
    private long id;
    private long customerId;
    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime createdAt;
}
