package com.okstatelibrary.spacesui.config;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.globals.edmonLowGlobalConfigs;
import com.okstatelibrary.spacesui.globals.vetMetGlobalConfigs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfigsFactory {

	@Value("${instanceName:edmon-low}") // fallback to "edmon-low" if missing
	private String instanceName;

	@Bean
	public GlobalConfigs getConfig() {

		switch (instanceName) {

		case "edmon-low":
			return new edmonLowGlobalConfigs();
		case "vet-med":
			return new vetMetGlobalConfigs();

		default:
			System.out.println("Unknown category.");
		}

		return null;

	}

}
