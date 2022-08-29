package com.antonromanov.arnote.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
            try {
                chain.doFilter(requestWrapper, responseWrapper);
            } finally {
                performRequestAudit(requestWrapper);
                performResponseAudit(responseWrapper);
            }
        } else {
            chain.doFilter(request, response);
        }
    }


    private void performRequestAudit(ContentCachingRequestWrapper requestWrapper) {
        if (requestWrapper != null && requestWrapper.getContentAsByteArray() != null && requestWrapper.getContentAsByteArray().length > 0) {
            ServletServerHttpRequest req = new ServletServerHttpRequest((HttpServletRequest) requestWrapper.getRequest());
            log.info("==== Request Logger ===== ");
            log.info("METHOD:: {} \n URL:: {} \n Request Body:: {}", req.getMethod(), req.getURI(), getPayLoadFromByteArray(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
        }
    }

    private void performResponseAudit(ContentCachingResponseWrapper responseWrapper) throws IOException {
        if (responseWrapper != null && responseWrapper.getContentAsByteArray() != null && responseWrapper.getContentAsByteArray().length > 0) {
            log.info("\n Response Body:: {}", getPayLoadFromByteArray(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding()));
        } else {
            performErrorResponseAudit(responseWrapper);
        }
        responseWrapper.copyBodyToResponse();
    }

    private void performErrorResponseAudit(ContentCachingResponseWrapper responseWrapper) {
        log.warn("HTTP Error Status Code::" + responseWrapper.getStatus());
    }

    private String getPayLoadFromByteArray(byte[] requestBuffer, String charEncoding) {
        String payLoad = "";
        try {
            payLoad = new String(requestBuffer, charEncoding);
        } catch (UnsupportedEncodingException unex) {
            payLoad = "Unsupported-Encoding";
        }
        return payLoad;
    }
}
