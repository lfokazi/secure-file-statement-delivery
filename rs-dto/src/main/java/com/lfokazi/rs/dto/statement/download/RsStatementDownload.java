package com.lfokazi.rs.dto.statement.download;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class RsStatementDownload {
    private long id;
    private long statementId;
    private String userRequestedId;
    private LocalDateTime dateRequested;
    private String downloadUrl;
    private Duration duration;
    private LocalDateTime expiresAt;
}
