package com.lfokazi.rs.controller.document.customer.statement;

import com.lfokazi.rs.controller.RsBaseIntegrationTest;
import com.lfokazi.rs.path.RsPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RsStatementControllerTest extends RsBaseIntegrationTest {
    private static final String TEST_FILE_NAME = "monthly_statement.html";
    private static final String TEST_FILE_PATH = "/statements/" + TEST_FILE_NAME;

    @Test
    void upload_success() throws Exception {
        mvc.perform(multipart(RsPath.CUSTOMER_STATEMENTS + "/upload")
                        .file(getFile("fileData", TEST_FILE_PATH))
                        .param("customerId", "2002")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void upload_unknown_customer() throws Exception {
        mvc.perform(multipart(RsPath.CUSTOMER_STATEMENTS + "/upload")
                        .file(getFile("fileData", TEST_FILE_PATH))
                        .param("customerId", "9999")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Customer not found."));
    }

    @Test
    void getStatements_success() throws Exception {
        mvc.perform(get(RsPath.CUSTOMER_STATEMENTS)
                        .param("customerId", "2002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageInfo.size").value(10))
                .andExpect(jsonPath("pageInfo.totalElements").value(4))
                .andExpect(jsonPath("content[0].id").value(3008))
                .andExpect(jsonPath("content[0].customerId").value(2002))
                .andExpect(jsonPath("content[0].customerFirstName").value("Robert"))
                .andExpect(jsonPath("content[0].customerLastName").value("Johnson"));
    }

    @Test
    void getDownloads_success() throws Exception {
        mvc.perform(get(RsPath.CUSTOMER_STATEMENTS + "/downloads")
                        .param("customerId", "2002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageInfo.size").value(10))
                .andExpect(jsonPath("pageInfo.totalElements").value(5))
                .andExpect(jsonPath("content[0].id").value(4009))
                .andExpect(jsonPath("content[0].userRequestedId").value("auth0:customer003"));
    }
}
