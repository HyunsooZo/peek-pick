package com.peekpick.shared.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Objects;

@Aspect
@Component
public class OutBoundLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(InboundLoggingAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Around("within( com.peekpick..*.gateway..*)")
    public Object logHttpRequests(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        HttpServletResponse response = attributes != null ? attributes.getResponse() : null;
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(Objects.requireNonNull(request));
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(Objects.requireNonNull(response));
        logRequest(wrappedRequest, joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            logException(ex);
            throw ex;
        }

        logResponse(wrappedResponse, result);
        wrappedResponse.copyBodyToResponse();
        return result;
    }

    private void logRequest(
            HttpServletRequest request,
            Object[] args
    ) {
        if (request != null) {
            logger.info(
                    "[Http External Request] HTTP Method: {}, URL: {}, Body: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    toPrettyJson(args)
            );
        }
    }

    private void logResponse(
            HttpServletResponse response,
            Object result
    ) {
        if (response != null) {
            logger.info("[Http External Response] Status: {},Result: {}", response.getStatus(), toPrettyJson(result));
        }
    }

    private void logException(Throwable ex) {
        logger.error("Exception occurred: ", ex);
    }

    private String toPrettyJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return "Unable to convert to JSON: " + e.getMessage();
        }
    }
}
