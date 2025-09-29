package com.lfokazi.service.statement.save.upload;

import com.lfokazi.domain.customer.Customer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
class StatementUploadAsyncDelegate {
    private final StatementUploadDelegate delegate;

    @Async
    @WithSpan
    void uploadAsync(@Nonnull Customer customer, @Nonnull MultipartFile file) {
        delegate.upload(customer, file);
    }
}
