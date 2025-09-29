package com.lfokazi;

import com.lfokazi.config.TestcontainersConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.lfokazi")
@Import(TestcontainersConfig.class)
@RequiredArgsConstructor
public class TestApplication {
}
