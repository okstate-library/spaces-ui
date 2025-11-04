package com.okstatelibrary.spacesui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;

@ControllerAdvice
public class GlobalModelAttributes {

	@Autowired
	private GlobalConfigs globalConfigs;

	@ModelAttribute
	public void addGlobalAttributes(Model model) {

		model.addAttribute("showExternalLinks", globalConfigs.displayExternalLinks());
		model.addAttribute("pageTitle", globalConfigs.getTitle());
		model.addAttribute("organizationName", globalConfigs.getOrganizationName());
		model.addAttribute("numberofTimeSlots", globalConfigs.getNumberofTimeSlots());
		model.addAttribute("helpInfoPath", "common/" + globalConfigs.getInstanceName() + "/helpinfo");
		model.addAttribute("showExtra", "false");
		model.addAttribute("roomPolicyInfoPath", "common/" + globalConfigs.getInstanceName() + "/room-policy");
		model.addAttribute("helpDeskName", globalConfigs.getHelpDeskName());
		model.addAttribute("policyUrl", globalConfigs.getPolicyUrl());
		model.addAttribute("summaryHelpInfoPath", "common/" + globalConfigs.getInstanceName() + "/summary-helpinfo");
	}

	@ExceptionHandler(Exception.class)
	public String handleAllExceptions(Exception ex) {
		ex.printStackTrace(); // prints full stack trace
		return "error"; // redirect to error.html
	}
}
