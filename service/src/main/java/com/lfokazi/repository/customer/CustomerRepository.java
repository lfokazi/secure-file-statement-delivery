package com.lfokazi.repository.customer;

import com.lfokazi.domain.customer.Customer;
import com.lfokazi.domain.statement.Statement;
import com.lfokazi.repository.BaseRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends BaseRepository<Customer> {
}
