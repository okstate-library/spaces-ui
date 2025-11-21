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
	 * Returns the seat options for the given category.
	 *
	 * @return a map of seat count options
	 */
	@Override
	public Map<String, String> getSeatList() {
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
	 * @return "true" indicating the floor selection should be hidden
	 */
	@Override
	public String hideFloorSelection() {
		return "true";
	}

	/**
	 * Returns the title used for the browser or header.
	 *
	 * @return formatted title string
	 */
	@Override
	public String getTitle() {
		return "Spaces:Room Booking System - William E. Brock Memorial Library (Vet med) - Oklahoma State University";
	}

	/**
	 * Returns the page organization name for the UI.
	 *
	 * @return A string representing the organization name.
	 */
	@Override
	public String getOrganizationName() {
		return "William E. Brock Memorial Library - Oklahoma State University";
	}

	/**
	 * Returns the number of bookable time slots.
	 *
	 * @return "4"
	 */
	@Override
	public int getNumberofTimeSlots() {
		return 4;
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