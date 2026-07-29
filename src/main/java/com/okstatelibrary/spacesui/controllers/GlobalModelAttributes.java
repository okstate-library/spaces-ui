package com.okstatelibrary.spacesui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.tenant.TenantConfigRegistry;

@ControllerAdvice
public class GlobalModelAttributes {

//	@Autowired
//	private GlobalConfigs globalConfigs;

	@Autowired
	private final TenantConfigRegistry registry;

	public GlobalModelAttributes(TenantConfigRegistry registry) {
		// this.globalConfigs = null;
		this.registry = registry;
		// this.globalConfigs = registry.getCurrentConfig();
	}

	@ModelAttribute
	public void addGlobalAttributes(Model model) {

		System.out.println("Call addGlobalAttributes");

		GlobalConfigs globalConfigs = registry.getCurrentConfig();

		model.addAttribute("showExternalLinks", globalConfigs.displayExternalLinks());
		model.addAttribute("pageTitle", globalConfigs.getTitle());
		model.addAttribute("organizationName", globalConfigs.getOrganizationName());
		model.addAttribute("numberofTimeSlots", globalConfigs.getNumberofTimeSlots());
		model.addAttribute("numberofTHoursCanBook", globalConfigs.getNumberofTimeSlots() / 2);
		model.addAttribute("helpInfoPath", "common/" + globalConfigs.getInstanceName() + "/helpinfo");
		model.addAttribute("showExtra", "false");
		model.addAttribute("roomPolicyInfoPath", "common/" + globalConfigs.getInstanceName() + "/room-policy");
		model.addAttribute("helpDeskName", globalConfigs.getHelpDeskName());
		model.addAttribute("policyUrl", globalConfigs.getPolicyUrl());
		model.addAttribute("summaryHelpInfoPath", "common/" + globalConfigs.getInstanceName() + "/summary-helpinfo");
		model.addAttribute("mainNavigationPath", "common/" + globalConfigs.getInstanceName() + "/main-nav");
		model.addAttribute("breadcrumbPath", "common/" + globalConfigs.getInstanceName() + "/breadcrumb");
		model.addAttribute("pageTitlePath", "common/" + globalConfigs.getInstanceName() + "/page-title");

	}

	@ExceptionHandler(Exception.class)
	public String handleAllExceptions(Exception ex) {
		ex.printStackTrace(); // prints full stack trace
		return "error"; // redirect to error.html
	}
}
