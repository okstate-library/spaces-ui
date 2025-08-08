package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code DscGlobalConfigs} implements {@link GlobalConfigs} and provides
 * configuration values for the Digital Scholarship Center (DSC) room
 * reservation system at Oklahoma State University.
 *
 * <p>
 * This class defines UI options such as floor and seat dropdowns, category
 * metadata, instance identifiers, and control flags used to customize the
 * behavior of the booking interface.
 * </p>
 */
public class DscGlobalConfigs implements GlobalConfigs {

	/**
	 * Floor list used in the floor selection dropdown.
	 */
	private static final Map<String, String> floorList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("0", "Any");
		}
	};

	/**
	 * Seat list used in the seat count dropdown.
	 */
	private static final Map<String, String> seatList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "1+");
			put("4", "3+");
		}
	};

	/**
	 * The category number associated with the Digital Scholarship Center.
	 */
	private static final String categoryNumber = "7030";

	/**
	 * Map of available categories. Maps the category number to a human-readable
	 * name.
	 */
	private static final Map<String, String> categoryList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(categoryNumber, "DSC");
		}
	};

	/**
	 * Returns the predefined seat count options.
	 *
	 * @param categoryNumber the category ID (not used in this implementation)
	 * @return a map representing seat options.
	 */
	@Override
	public Map<String, String> getSeatList(String categoryNumber) {
		return seatList;
	}

	/**
	 * Returns the predefined floor list.
	 *
	 * @return a map representing floor options.
	 */
	@Override
	public Map<String, String> getFloorList() {
		return floorList;
	}

	/**
	 * Returns the unique instance name used in routing or configuration.
	 *
	 * @return the string "edmon-low".
	 */
	@Override
	public String getInstanceName() {
		return "edmon-low";
	}

	/**
	 * Returns the available categories for this instance.
	 *
	 * @return a map representing categories.
	 */
	@Override
	public Map<String, String> getCategoryList() {
		return categoryList;
	}

	/**
	 * Returns the unique category number for DSC.
	 *
	 * @return the category ID string.
	 */
	@Override
	public String getCategoryNumber() {
		return categoryNumber;
	}

	/**
	 * Determines whether the floor selection dropdown should be hidden.
	 *
	 * @param categoryId the category ID (not used in this implementation)
	 * @return "true" to hide the floor dropdown.
	 */
	@Override
	public String hideFloorSelection(String categoryId) {
		return "true";
	}

	/**
	 * Determines whether the category selection dropdown should be hidden.
	 *
	 * @param categoryId the category ID (not used in this implementation)
	 * @return "false" to show the category dropdown.
	 */
	@Override
	public String hideCategorySelection(String categoryId) {
		return "false";
	}

	/**
	 * Checks if the given categoryId is valid for this configuration. Always
	 * returns true in this implementation.
	 *
	 * @param categoryId the category ID to check
	 * @return true
	 */
	@Override
	public boolean checkCategory(String categoryId) {
		return true;
	}

	/**
	 * Returns the display title for the DSC booking page.
	 *
	 * @return the formatted title string.
	 */
	@Override
	public String getTitle() {
		return "Spaces:Room Booking System - Digital Scholarship Center - Oklahoma State University";
	}

	/**
	 * Returns the number of allowed booking time slots.
	 *
	 * @return "8" as a string.
	 */
	@Override
	public String getNumberofTimeSlots() {
		return "8";
	}

	/**
	 * Returns the help desk name shown on the interface.
	 *
	 * @return the string "Digital Scholarship Center Help Desk."
	 */
	@Override
	public String getHelpDeskName() {
		return "Digital Scholarship Center Help Desk.";
	}

	/**
	 * Returns the URL to the room booking policy page.
	 *
	 * @return the policy URL.
	 */
	@Override
	public String getPolicyUrl() {
		return "https://info.library.okstate.edu/studyrooms/policies";
	}

	/**
	 * Indicates whether external links should be displayed in the UI or not.
	 *
	 * <p>
	 * This implementation always returns {@code false}, meaning that external links
	 * are not displayed. Override this method to change the default behavior if
	 * external links need to be shown.
	 * </p>
	 *
	 * @return {@code false} indicating external links are not to be displayed
	 */
	@Override
	public String displayExternalLinks() {
		return "false";
	}
}
