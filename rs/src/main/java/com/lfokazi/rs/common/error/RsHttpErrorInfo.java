package com.lfokazi.rs.common.error;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@Jacksonized
@Getter
public class RsHttpErrorInfo {
  private final String traceId;
  private final ZonedDateTime timestamp;
  private final String utlPath;
  private final HttpStatus status;
  private final String message;
}
