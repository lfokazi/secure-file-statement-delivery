package com.lfokazi.rs.controller;

import com.lfokazi.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class RsBaseIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
