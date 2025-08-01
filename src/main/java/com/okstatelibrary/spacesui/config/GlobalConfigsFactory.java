package com.okstatelibrary.spacesui.config;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.globals.ArchitectureGlobalConfigs;
import com.okstatelibrary.spacesui.globals.DscGlobalConfigs;
import com.okstatelibrary.spacesui.globals.EdmonLowGlobalConfigs;
import com.okstatelibrary.spacesui.globals.VetMetGlobalConfigs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Factory configuration class for providing the appropriate
 * {@link GlobalConfigs} implementation based on the application instance name.
 *
 * <p>
 * The instance name is determined by the property {@code instanceName} defined
 * in the {@code application.properties} file. This allows dynamic selection of
 * configuration objects tailored to different library branches or application
 * instances.
 * </p>
 *
 * <p>
 * Supported instance names:
 * <ul>
 * <li>edmon-low</li>
 * <li>vet-med</li>
 * <li>architecture</li>
 * <li>dsc</li>
 * </ul>
 * </p>
 */
@Configuration
public class GlobalConfigsFactory {

	com.okstatelibrary.spacesui.util.SystemProperties systemProperties;
	
    /**
     * Injected instance name from configuration (e.g., application.properties).
     * Defaults to "edmon-low" if the property is not specified.
     */
    @Value("${instanceName:edmon-low}")
    private String instanceName;

	/**
	 * Returns a bean of type {@link GlobalConfigs} based on the configured instance
	 * name.
	 *
	 * @return an implementation of {@link GlobalConfigs} depending on the
	 *         {@code instanceName}
	 * @throws IllegalArgumentException if the instance name is unrecognized
	 */
	@Bean
	public GlobalConfigs getConfig() {

		switch (instanceName) {
		case "edmon-low":
			return new EdmonLowGlobalConfigs();
		case "vet-med":
			return new VetMetGlobalConfigs();
		case "architecture":
			return new ArchitectureGlobalConfigs();
		case "dsc":
			return new DscGlobalConfigs();
		default:
			System.err.println("Unknown instance name: " + systemProperties.getInstanceName());
			return null;
		}
	}
}