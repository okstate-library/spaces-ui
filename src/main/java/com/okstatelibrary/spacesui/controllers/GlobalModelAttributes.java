package com.okstatelibrary.spacesui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.tenant.TenantConfigRegistry;

/**
 *
 */
@ControllerAdvice
public class GlobalModelAttributes {


    /**
     *
     */
    @Autowired
    private final TenantConfigRegistry registry;

    /**
     *
     * @param registry
     */
    public GlobalModelAttributes(TenantConfigRegistry registry) {
        this.registry = registry;
    }

    /**
     *
     * @param model
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
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return "error";
    }
}
