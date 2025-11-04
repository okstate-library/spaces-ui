package com.okstatelibrary.spacesui.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.okstatelibrary.spacesui.config.SessionListener;

@RestController
@RequestMapping("/api")
public class ApiResource {

	@RequestMapping(value = "/getRemainTime", method = RequestMethod.GET)
	public int getRemainingSessionTime(HttpServletRequest request) {

		System.out.println("Active sessions: " + SessionListener.getActiveSessions());

		return SessionListener.getActiveSessions();

	}

}
