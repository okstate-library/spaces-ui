package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

/**
 * EdmonLowGlobalConfigs is a concrete implementation of the GlobalConfigs
 * interface. It provides configuration values for the Edmon Low Library and
 * Creative Studios instances, including dropdown lists for floors, seats, and
 * categories, as well as UI control flags and metadata like policy URLs and
 * titles.
 */
public class EdmonLowGlobalConfigs implements GlobalConfigs {

	/**
	 * Defines the floor dropdown list for Edmon Low Library.
	 */
	private static final Map<String, String> floorList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("0", "Any");
			put("1", "First");
			put("2", "Second");
			put("3", "Third");
		}
	};

	/**
	 * Seat count dropdown list specific to Edmon Low Library.
	 */
	private static final Map<String, String> edmonLowSeatList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "1+");
			put("2", "2+");
			put("4", "4+");
			put("6", "6+");
			put("8", "8+");
			put("10", "10+");
		}
	};

	/**
	 * Seat count dropdown list specific to Creative Studios.
	 */
	private static final Map<String, String> creativeStuiodSeatList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "1+");
			put("3", "2+");
		}
	};

	/**
	 * Category number representing Edmon Low Library.
	 */
	private static final String edmonLowLibraryCategoryNumber = "7030";

	/**
	 * Category number representing Creative Studios.
	 */
	private static final String creativeStudioCategoryNumber = "7032";

	/**
	 * Category dropdown list for Edmon Low Library and Creative Studios.
	 */
	private static final Map<String, String> edmonLowCategoryList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(edmonLowLibraryCategoryNumber, "Edmon Low Library");
			put(creativeStudioCategoryNumber, "Creative Studios");
		}
	};

	/**
	 * Returns the appropriate seat list depending on the category.
	 *
	 * @param categoryNumber The category ID.
	 * @return A map of seat size options.
	 */
	@Override
	public Map<String, String> getSeatList(String categoryNumber) {
		if (categoryNumber.equals(edmonLowLibraryCategoryNumber)) {
			return edmonLowSeatList;
		} else {
			return creativeStuiodSeatList;
		}
	}

	/**
	 * Returns the floor list for Edmon Low Library.
	 *
	 * @return A map of floor options.
	 */
	@Override
	public Map<String, String> getFloorList() {
		return floorList;
	}

	/**
	 * Returns the instance name used for conditional configuration.
	 *
	 * @return The string "edmon-low".
	 */
	@Override
	public String getInstanceName() {
		return "edmon-low";
	}

	/**
	 * Returns the list of categories supported in this configuration.
	 *
	 * @return A map of category IDs to names.
	 */
	@Override
	public Map<String, String> getCategoryList() {
		return edmonLowCategoryList;
	}

	/**
	 * Returns the default category number.
	 *
	 * @return The category number for Edmon Low Library.
	 */
	@Override
	public String getCategoryNumber() {
		return edmonLowLibraryCategoryNumber;
	}

	/**
	 * Determines whether floor selection should be hidden for a category.
	 *
	 * @param categoryId The category ID.
	 * @return "false" if Edmon Low Library, otherwise "true".
	 */
	@Override
	public String hideFloorSelection(String categoryId) {
		if (categoryId.equals(edmonLowLibraryCategoryNumber)) {
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * Determines whether category selection should be hidden for a category.
	 *
	 * @param categoryId The category ID.
	 * @return Always returns "false" indicating the category selection is shown.
	 */
	@Override
	public String hideCategorySelection(String categoryId) {
		return "false";
	}

	/**
	 * Checks if a given category ID is supported in this configuration.
	 *
	 * @param categoryId The category ID.
	 * @return True if category exists in the configuration.
	 */
	@Override
	public boolean checkCategory(String categoryId) {
		return edmonLowCategoryList.containsKey(categoryId);
	}

	/**
	 * Returns the page title for the UI.
	 *
	 * @return A string representing the browser title bar.
	 */
	@Override
	public String getTitle() {
		return "Spaces:Room Booking System - Edmon Low Library - Oklahoma State University";
	}

	/**
	 * Returns the number of allowed booking time slots.
	 *
	 * @return The string "8".
	 */
	@Override
	public String getNumberofTimeSlots() {
		return "8";
	}

	/**
	 * Returns the name of the help desk for display purposes.
	 *
	 * @return The help desk name string.
	 */
	@Override
	public String getHelpDeskName() {
		return "OSU Library Circulation Desk.";
	}

	/**
	 * Returns the URL to the room booking policy page.
	 *
	 * @return A URL string to the library study room policies.
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
		return "true";
	}
}
