package com.okstatelibrary.spacesui.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String host = request.getHeader("Host");

        String tenant = extractTenant(host);

        TenantContext.setTenant(tenant);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
    }

    private String extractTenant(String host) {

        if (host == null) {
            return "default";
        }

        host = host.split(":")[0];

        String[] parts = host.split("\\.");

        if (parts.length >= 2) {
            return parts[0];
        }

        return "spacest";
    }

}
