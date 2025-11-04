package com.okstatelibrary.spacesui.globals;

import java.util.Map;

/**
 * GlobalConfigs is an interface for defining configuration values and behavior
 * specific to different application instances (e.g., different libraries or
 * departments).
 *
 * <p>
 * Implementing classes provide customized values for things like
 * seat/floor/category lists, policy URLs, instance titles, and visibility rules
 * for different UI sections.
 *
 * <p>
 * This interface allows the application to dynamically switch behavior and
 * content based on the current instance (e.g., "edmon-low", "vet-med", etc.).
 */
public interface GlobalConfigs {

	/**
	 * Gets the URL to the room or space usage policy.
	 *
	 * @return the policy document URL as a String.
	 */
	String getPolicyUrl();

	/**
	 * Returns the name of the help desk or support unit for the current instance.
	 *
	 * @return help desk name.
	 */
	String getHelpDeskName();

	/**
	 * Returns the number of allowable time slots as a String. Could represent a
	 * label or numeric value to be parsed elsewhere.
	 *
	 * @return number of time slots.
	 */
	String getNumberofTimeSlots();

	/**
	 * Checks whether external links can display or not to this instance.
	 *
	 * @return true if the extranl links can display in the home page; false
	 *         otherwise.
	 */
	String displayExternalLinks();

	/**
	 * Returns the display title (e.g., used on web page headers) for the current
	 * instance.
	 *
	 * @return title string.
	 */
	String getTitle();
	
	/**
	 * Returns the display organization Name for the current
	 * instance.
	 *
	 * @return organization name string.
	 */
	String getOrganizationName();

	/**
	 * Returns the unique category number identifying this instance.
	 *
	 * @return category number as String.
	 */
	String getCategoryNumber();

	/**
	 * Returns the instance name (e.g., "edmon-low", "vet-med").
	 *
	 * @return instance name as String.
	 */
	String getInstanceName();

	/**
	 * Returns a map of available seats for the specified category.
	 *
	 * @param categoryId the ID of the category.
	 * @return a map of seat IDs to seat labels.
	 */
	Map<String, String> getSeatList();

	/**
	 * Returns a map of floor IDs to floor names for the instance.
	 *
	 * @return floor map.
	 */
	Map<String, String> getFloorList();

	/**
	 * Indicates whether the floor selection UI should be hidden for the given
	 * category.
	 *
	 * @param categoryId the ID of the category.
	 * @return a flag (e.g., "true"/"false") as String to indicate visibility.
	 */
	String hideFloorSelection();
}