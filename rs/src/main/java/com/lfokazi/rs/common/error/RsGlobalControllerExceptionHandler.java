package com.lfokazi.rs.common.error;

import com.lfokazi.core.exception.BadRequestException;
import com.lfokazi.core.exception.InternalErrorException;
import com.lfokazi.core.exception.ObjectNotFoundException;
import io.opentelemetry.api.trace.Span;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
class RsGlobalControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody RsHttpErrorInfo handleBadRequestExceptions(HttpServletRequest request,
                                                                    BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public @ResponseBody RsHttpErrorInfo handleNotFoundExceptions(HttpServletRequest request,
                                                                  ObjectNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalErrorException.class)
    public @ResponseBody RsHttpErrorInfo handleInternalErrorExceptions(HttpServletRequest request,
                                                                       InternalErrorException ex) {
        log.error(ex.getMessage(), ex);
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public @ResponseBody RsHttpErrorInfo handleValidationException(HttpServletRequest request,
                                                                   HandlerMethodValidationException ex) {
        log.error(ex.getMessage(), ex);
        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    private RsHttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, Exception ex) {
        var path = request.getRequestURI();
        var message = ex.getMessage();

        return RsHttpErrorInfo.builder()
                .traceId(Span.current().getSpanContext().getTraceId())
                .timestamp(ZonedDateTime.now())
                .utlPath(path)
                .status(httpStatus)
                .message(message)
                .build();
    }
}
