package com.lfokazi.domain.customer;

import com.lfokazi.domain.statement.Statement;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @CreatedDate
    private LocalDateTime created;

    private String userId;

    @Nonnull
    private String firstName;

    @Nonnull
    private String lastName;

    /**
     * List of statements for this customer
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Statement> statements = new ArrayList<>();
}
