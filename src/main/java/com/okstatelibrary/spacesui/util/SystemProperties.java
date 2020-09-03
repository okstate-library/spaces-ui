package com.okstatelibrary.spacesui.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:application.properties")
public class SystemProperties {
	
	String springShareSecretkey;

	public String getSpringShareSecretkey() {
		return springShareSecretkey;
	}

	public void setSpringShareSecretkey(String springShareSecretkey) {
		this.springShareSecretkey = springShareSecretkey;
	}
	
	String springShareClientId;

	public String getSpringShareClientId() {
		return springShareClientId;
	}

	public void setSpringShareClientId(String springShareClientId) {
		this.springShareClientId = springShareClientId;
	}
	
}
