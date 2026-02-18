package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
@WebFilter("/*")
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoggingFilter initialized");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        System.out.println("Request received for: " + request.getRemoteAddr());
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        System.out.println("Request processed in " + (endTime - startTime) + " ms");
    }
    @Override
    public void destroy() {
        System.out.println("LoggingFilter destroyed");
    }
}