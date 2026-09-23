package com.okstatelibrary.spacesui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.tenant.TenantConfigRegistry;

/**
 * Provides global model attributes that are automatically available to
 * all Spring MVC views.
 *
 * <p>This class retrieves the configuration for the current tenant and
 * adds commonly used configuration values to the {@link Model}. This
 * avoids the need to add the same attributes individually in each
 * controller.</p>
 *
 * <p>The class also provides centralized exception handling for
 * unhandled exceptions.</p>
 *
 */
@ControllerAdvice
public class GlobalModelAttributes {


    /**
     * Registry responsible for retrieving configuration settings for
     * the current tenant.
     *
     */
    private final TenantConfigRegistry registry;

    /**
     * Creates an instance of {@code GlobalModelAttributes}.
     *
     * @param registry the registry used to retrieve the current tenant's configuration
     */
    public GlobalModelAttributes(TenantConfigRegistry registry) {
        this.registry = registry;
    }

    /**
     * Adds common configuration values to the model so they are available * to all application views.
     *
     * <p>The attributes include page information, organization details,
     * booking settings, help desk information, policy URLs, and tenant- * specific configuration values.</p>
     *
     * @param model the Spring MVC model used to pass attributes to views
     *
     *
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model) {

        System.out.println("Call addGlobalAttributes");

        GlobalConfigs globalConfigs = registry.getCurrentConfig();

        model.addAttribute("showExternalLinks", globalConfigs.displayExternalLinks());
        model.addAttribute("pageTitle", globalConfigs.getTitle());
        model.addAttribute("organizationName", globalConfigs.getOrganizationName());
        model.addAttribute("numberofTimeSlots", globalConfigs.getNumberofTimeSlots());
        model.addAttribute("numberofTHoursCanBook", globalConfigs.getNumberofTimeSlots() / 2);
        model.addAttribute("showExtra", "false");
        model.addAttribute("helpDeskName", globalConfigs.getHelpDeskName());
        model.addAttribute("policyUrl", globalConfigs.getPolicyUrl());
        model.addAttribute("instanceName", globalConfigs.getInstanceName());

    }

    /**
     * Handles unhandled exceptions that occur during request processing.
     *
     * <p>The exception is logged and the user is directed to the * application's error page.</p>
     *
     * @param ex the exception that was thrown during request processing * @return the name of the error view
     *
     *
     */
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return "error";
    }
}
