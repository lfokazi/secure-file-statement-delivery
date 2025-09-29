package com.lfokazi.service.statement.save.upload;

import com.lfokazi.core.exception.ObjectNotFoundException;
import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.repository.customer.CustomerRepository;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class StatementUploaderImpl implements StatementUploader {
    private final CustomerRepository customerRepository;
    private final StatementUploadDelegate delegate;
    private final StatementUploadAsyncDelegate asyncDelegate;

    @Override
    public void uploadAsync(long customerId, @Nonnull MultipartFile file) {
        var customer = getCustomer(customerId);
        asyncDelegate.uploadAsync(customer, file);
    }

    @Timed
    @Override
    public Statement upload(long customerId, @Nonnull MultipartFile file) {
        return delegate.upload(getCustomer(customerId), file);
    }

    private Customer getCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException("Customer not found."));
    }
}
