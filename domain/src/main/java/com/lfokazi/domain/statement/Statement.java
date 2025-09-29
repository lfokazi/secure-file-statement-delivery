package com.lfokazi.domain.statement;

import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.download.StatementDownload;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Statement {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @CreationTimestamp
    private LocalDateTime created;

    String userCreatedId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private Customer customer;

    /**
     * List of statements for this customer
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statement", fetch = FetchType.LAZY)
    @Builder.Default
    private List<StatementDownload> downloads = new ArrayList<>();

    private String bucketName;

    private String objectKey;
}
