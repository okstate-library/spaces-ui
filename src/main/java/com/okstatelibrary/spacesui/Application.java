
package com.okstatelibrary.spacesui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.okstatelibrary.spacesui.util.RibbonMessage;

/**
 * @author Damith Perera
 *
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	/**
	 * Spring Application builder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

//		RibbonMessage.messageType = "info";
//		RibbonMessage.message = "Sample message";
		
		for (String arg : args) {

			if (arg.contains("mtype")) {
				String[] str = arg.split("=");

				RibbonMessage.messageType = str[1];

			} else if (arg.contains("msg")) {
				String[] str = arg.split("=");

				RibbonMessage.message = str[1];
			}

		}

		SpringApplication.run(Application.class, args);
	}

}