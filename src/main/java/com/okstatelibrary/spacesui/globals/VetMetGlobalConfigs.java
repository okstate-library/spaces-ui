package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link GlobalConfigs} interface for the Vet Med
 * (William E. Brock Memorial Library) configuration. This class provides
 * specific configuration data such as category ID, seat list, and UI visibility
 * preferences related to the Vet Med instance.
 */
public class VetMetGlobalConfigs implements GlobalConfigs {

	/**
	 * The unique category number for Vet Med.
	 */
	private static final String vetMedCategoryNumber = "7031";

	/**
	 * Defines the Vet Med seat count drop-down list. Key represents the minimum
	 * seat count, and value is the label to display.
	 */
	private static final Map<String, String> vetMedSeatList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put("1", "1+");
		}
	};

	/**
	 * Defines the category list available for Vet Med instance. Key is the category
	 * number, value is the category label.
	 */
	private static final Map<String, String> vetMedCategoryList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(vetMedCategoryNumber, "Vet med");
		}
	};

	/**
	 * Returns the seat options for the given category.
	 *
	 * @param categoryId the category identifier
	 * @return a map of seat count options
	 */
	@Override
	public Map<String, String> getSeatList(String categoryId) {
		return vetMedSeatList;
	}

	/**
	 * Returns null since floor selection is hidden for Vet Med.
	 *
	 * @return null
	 */
	@Override
	public Map<String, String> getFloorList() {
		return null;
	}

	/**
	 * Returns the instance name for this configuration.
	 *
	 * @return "vet-med"
	 */
	@Override
	public String getInstanceName() {
		return "vet-med";
	}

	/**
	 * Returns the list of categories available for Vet Med.
	 *
	 * @return map of category ID to name
	 */
	@Override
	public Map<String, String> getCategoryList() {
		return vetMedCategoryList;
	}

	/**
	 * Returns the default category number for Vet Med.
	 *
	 * @return category number as string
	 */
	@Override
	public String getCategoryNumber() {
		return vetMedCategoryNumber;
	}

	/**
	 * Indicates whether the floor selection UI should be hidden.
	 *
	 * @param categoryId the category identifier
	 * @return "true" indicating the floor selection should be hidden
	 */
	@Override
	public String hideFloorSelection(String categoryId) {
		return "true";
	}

	/**
	 * Indicates whether the category selection UI should be hidden.
	 *
	 * @param categoryId the category identifier
	 * @return "true" indicating the category selection should be hidden
	 */
	@Override
	public String hideCategorySelection(String categoryId) {
		return "true";
	}

	/**
	 * Verifies if the given category ID exists in the configuration.
	 *
	 * @param categoryId the category identifier
	 * @return true if category exists, otherwise false
	 */
	@Override
	public boolean checkCategory(String categoryId) {
		return vetMedCategoryList.containsKey(categoryId);
	}

	/**
	 * Returns the title used for the browser or header.
	 *
	 * @return formatted title string
	 */
	@Override
	public String getTitle() {
		return "Spaces:Room Booking System - William E. Brock Memorial Library Edmon Low Library (Vet med) - Oklahoma State University";
	}

	/**
	 * Returns the number of bookable time slots.
	 *
	 * @return "4"
	 */
	@Override
	public String getNumberofTimeSlots() {
		return "4";
	}

	/**
	 * Returns the name of the help desk associated with this instance.
	 *
	 * @return help desk name string
	 */
	@Override
	public String getHelpDeskName() {
		return "Vet Med Library Circulation and Information Desk";
	}

	/**
	 * Returns the URL to the study room policy page.
	 *
	 * @return policy URL as string
	 */
	@Override
	public String getPolicyUrl() {
		return "https://info.library.okstate.edu/veterinary-health-sciences/studyrooms/policies";
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