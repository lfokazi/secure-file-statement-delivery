package com.lfokazi.domain.statement.download;

import com.lfokazi.domain.statement.Statement;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatementDownload {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime dateRequested;

    @Nonnull
    String userRequestedId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private Statement statement;

    @Nonnull
    private String url;

    private long durationMs;
}
