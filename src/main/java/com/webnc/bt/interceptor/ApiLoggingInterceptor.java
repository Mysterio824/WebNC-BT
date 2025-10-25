package com.webnc.bt.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID; // Import UUID

// Use SLF4J's Key-Value logging. This works perfectly with LogstashEncoder.
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {

    // Get our custom "api.logger"
    private static final Logger log = LoggerFactory.getLogger("api.logger");
    private static final String REQUEST_ID_ATTR = "request_id";
    private static final String START_TIME_ATTR = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Start the timer
        long startTime = System.currentTimeMillis();
        // Generate a unique ID for this request
        String requestId = UUID.randomUUID().toString();

        // Store them in the request attributes to access later
        request.setAttribute(START_TIME_ATTR, startTime);
        request.setAttribute(REQUEST_ID_ATTR, requestId);

        // Log the "request arrived" event
        log.info("API Request Started",
                kv("trace_id", requestId),
                kv("http_method", request.getMethod()),
                kv("http_path", request.getRequestURI()),
                kv("client_ip", request.getRemoteAddr()),
                kv("user_agent", request.getHeader("User-Agent"))
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Get the start time and request ID from attributes
        long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        String requestId = (String) request.getAttribute(REQUEST_ID_ATTR);
        long duration = System.currentTimeMillis() - startTime;

        // Log the "request finished" event
        // We include all the data from preHandle PLUS the result
        log.info("API Request Finished",
                kv("trace_id", requestId),
                kv("http_method", request.getMethod()),
                kv("http_path", request.getRequestURI()),
                kv("http_status", response.getStatus()),
                kv("duration_ms", duration),
                kv("client_ip", request.getRemoteAddr()),
                kv("user_agent", request.getHeader("User-Agent")),
                kv("exception", ex != null ? ex.getMessage() : null)
        );
    }
}