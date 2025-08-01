package com.okstatelibrary.spacesui.folio.models;

/**
 * Represents a basic user with a username and password.
 * <p>
 * This class can be used for authentication, user management, or session
 * handling in applications that require basic user information.
 * </p>
 */
public class User {

	/**
	 * The unique username identifying the user.
	 */
	public String username;

	/**
	 * The password associated with the user.
	 * <p>
	 * <b>Note:</b> For production systems, avoid storing plain-text passwords.
	 * Always hash and salt passwords before storing or processing them.
	 * </p>
	 */
	public String password;
}
