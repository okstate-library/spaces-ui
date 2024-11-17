package com.okstatelibrary.spacesui.util;

import org.springframework.beans.factory.annotation.Value;
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

	String idpMetadataURL;

	public String getIdpMetadataURL() {
		return idpMetadataURL;
	}

	public void setIdpMetadataURL(String idpMetadataURL) {
		this.idpMetadataURL = idpMetadataURL;
	}

	String metadataEntityId;

	public String getMetadataEntityId() {
		return metadataEntityId;
	}

	public void setMetadataEntityId(String metadataEntityId) {
		this.metadataEntityId = metadataEntityId;
	}

	String categoryId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Value("${folioTenant}")
	private String folioTenant;

	public static String FolioTenant;

	/**
	 * @param folioTenant the folioTenant to set
	 */
	public void setFolioTenant(String folioTenant) {
		FolioTenant = folioTenant;
	}

	@Value("${folioURL}")
	private String folioURL;

	public static String FolioURL;

	/**
	 * @param folioURL the folioURL to set
	 */
	public void setFolioURL(String folioURL) {
		FolioURL = folioURL;
	}

	@Value("${folioUsername}")
	private String folioUsername;

	public static String FolioUsername;

	/**
	 * @param folioURL the folioURL to set
	 */
	public void setFolioUsername(String folioUsername) {
		FolioUsername = folioUsername;
	}

	@Value("${folioPassword}")
	private String folioPassword;

	public static String FolioPassword;

	/**
	 * @param folioURL the folioURL to set
	 */
	public void setFolioPassword(String folioPassword) {
		FolioPassword = folioPassword;
	}

}