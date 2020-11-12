
package com.okstatelibrary.spacesui.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.okstatelibrary.spacesui.core.CurrentUserHandlerMethodArgumentResolver;

/**
 * MVC configurations
 * @author Damith
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * 
	 */
	@Autowired
	CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;

	/**
	 *
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("pages/index");
	}

	/**
	 *
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/static/**")) {
			registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		}
	}

	/**
	 *
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserHandlerMethodArgumentResolver);
	}

	/**
	 *
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}