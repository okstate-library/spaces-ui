package com.okstatelibrary.spacesui.tenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String host = request.getHeader("Host");

		String tenant = extractTenant(host);

		TenantContext.setTenant(tenant);
		
//		TenantConfig config =
//	            tenantConfigService.getConfig(tenant);
//
//	    TenantContext.setTenantConfig(config);


		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		//TenantContext.clear();
	}

	private String extractTenant(String host) {

		if (host == null) {
			return "default";
		}

		host = host.split(":")[0];
		
		String[] parts = host.split("\\.");

		if (parts.length >= 2) {

			System.out.println("Tenant 3rd level  : " + parts[0]);
			
			return parts[0];

		}

		return "spacest";
	}

	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//		String uri = request.getRequestURI();
//
//		String[] parts = uri.split("/");
//
//		if (parts.length > 1) {
//			String tenant = parts[1];
//
//			TenantContext.setTenant(tenant);
//		}
//
//		return true;
//	}
	
	
}
