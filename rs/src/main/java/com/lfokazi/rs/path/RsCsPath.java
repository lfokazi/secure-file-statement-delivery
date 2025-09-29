package com.lfokazi.rs.path;

/**
 * This interface defines the base path and specific endpoints for the Customer Service (CS) API.
 * The assumption made was that after uploading a statement, admin would share the link with the customer.
 * This is just for demonstrating how we could segment our workforce APIs and customer APIs.
 */
public interface RsCsPath {
    String BASE = "/v1/api/cs";

    // Customer Documents
    String CUSTOMER_STATEMENTS = BASE + "/documents/customer/statements";;
}
