package com.okstatelibrary.spacesui.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * SystemProperties is a Spring-managed configuration component used for loading
 * and providing access to application-level configuration properties.
 *
 * <p>
 * This class loads values from the {@code application.properties} file via
 * Spring's @ConfigurationProperties and @Value annotations.
 * </p>
 *
 * <p>
 * Some properties are injected directly into static fields using setters,
 * allowing global static access throughout the application.
 * </p>
 *
 * <p>
 * Example property entries in application.properties:
 * </p>
 * 
 * <pre>
 * instanceName=MyAppInstance
 * springShareSecretkey=abc123
 * springShareClientId=my-client-id
 * idpMetadataURL=https://idp.example.com/metadata
 * metadataEntityId=urn:example:idp
 * folioTenant=mytenant
 * folioURL=https://folio.example.com
 * folioUsername=admin
 * folioPassword=secret
 * </pre>
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:application.properties")
public class SystemProperties {

	/**
	 * Name of the current instance/environment (e.g., development, staging,
	 * production).
	 */
	private String instanceName;

	/** Secret key used to access SpringShare APIs. */
	private String springShareSecretkey;

	/** Client ID used for authenticating with SpringShare. */
	private String springShareClientId;

	/** Identity Provider (IdP) metadata URL for SAML authentication. */
	private String idpMetadataURL;

	/** Entity ID defined in the SAML metadata. */
	private String metadataEntityId;

	/** FOLIO tenant identifier injected via application.properties. */
	@Value("${folioTenant}")
	private String folioTenant;
	public static String FolioTenant;

	/** Base URL of the FOLIO system. */
	@Value("${folioURL}")
	private String folioURL;
	public static String FolioURL;

	/** Username for FOLIO login. */
	@Value("${folioUsername}")
	private String folioUsername;
	public static String FolioUsername;

	/** Password for FOLIO login. */
	@Value("${folioPassword}")
	private String folioPassword;
	public static String FolioPassword;

	// ===== Getters and Setters =====

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getSpringShareSecretkey() {
		return springShareSecretkey;
	}

	public void setSpringShareSecretkey(String springShareSecretkey) {
		this.springShareSecretkey = springShareSecretkey;
	}

	public String getSpringShareClientId() {
		return springShareClientId;
	}

	public void setSpringShareClientId(String springShareClientId) {
		this.springShareClientId = springShareClientId;
	}

	public String getIdpMetadataURL() {
		return idpMetadataURL;
	}

	public void setIdpMetadataURL(String idpMetadataURL) {
		this.idpMetadataURL = idpMetadataURL;
	}

	public String getMetadataEntityId() {
		return metadataEntityId;
	}

	public void setMetadataEntityId(String metadataEntityId) {
		this.metadataEntityId = metadataEntityId;
	}

	/**
	 * Sets the folio tenant value and assigns it to the static field.
	 * 
	 * @param folioTenant the FOLIO tenant identifier
	 */
	public void setFolioTenant(String folioTenant) {
		FolioTenant = folioTenant;
	}

	/**
	 * Sets the folio URL and assigns it to the static field.
	 * 
	 * @param folioURL the FOLIO base URL
	 */
	public void setFolioURL(String folioURL) {
		FolioURL = folioURL;
	}

	/**
	 * Sets the FOLIO username and assigns it to the static field.
	 * 
	 * @param folioUsername the FOLIO username
	 */
	public void setFolioUsername(String folioUsername) {
		FolioUsername = folioUsername;
	}

	/**
	 * Sets the FOLIO password and assigns it to the static field.
	 * 
	 * @param folioPassword the FOLIO password
	 */
	public void setFolioPassword(String folioPassword) {
		FolioPassword = folioPassword;
	}
}