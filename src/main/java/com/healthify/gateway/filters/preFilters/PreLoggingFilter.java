package com.healthify.gateway.filters.preFilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PreLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(PreLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        logger.info(">>>> Incoming Request to PreLoggingFilter: ");
        logger.info("Method : {}", request.getMethod());
        logger.info("URI    : {}", request.getRequestURI());

        filterChain.doFilter(request, response);

        long time = System.currentTimeMillis() - start;

        logger.info("<<<< Response");
        logger.info("Status : {}", response.getStatus());
        logger.info("Time   : {} ms", time);
    }
}