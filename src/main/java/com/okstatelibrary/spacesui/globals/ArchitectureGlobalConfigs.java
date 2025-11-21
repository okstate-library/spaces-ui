package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ArchitectureGlobalConfigs} is an implementation of the
 * {@link GlobalConfigs} interface providing instance-specific configuration
 * values and UI control logic for the Architecture Library at Oklahoma State
 * University.
 * 
 * <p>
 * This class defines floor lists, seat lists, category IDs, display titles, and
 * visibility toggles used in the application UI.
 * </p>
 */
public class ArchitectureGlobalConfigs implements GlobalConfigs {

	/**
	 * Floor list used in dropdowns. Contains predefined floor options.
	 */
	private static final Map<String, String> floorList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("0", "Any");
		}
	};

	/**
	 * Seat list used in dropdowns. Contains predefined seat groupings.
	 */
	private static final Map<String, String> seatList = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "1+");
		}
	};

	/**
	 * The unique category number identifying the Architecture Library.
	 */
	private static final String categoryNumber = "40680";

	/**
	 * Returns the seat list map.
	 *
	 * @param categoryNumber category ID (not used in this implementation).
	 * @return a map of seat options.
	 */
	@Override
	public Map<String, String> getSeatList() {
		return seatList;
	}

	/**
	 * Returns the floor list map.
	 *
	 * @return a map of available floor options.
	 */
	@Override
	public Map<String, String> getFloorList() {
		return floorList;
	}

	/**
	 * Returns the instance name for this configuration.
	 *
	 * @return the string "architecture".
	 */
	@Override
	public String getInstanceName() {
		return "architecture";
	}

	/**
	 * Returns the category number.
	 *
	 * @return the category ID string for this instance.
	 */
	@Override
	public String getCategoryNumber() {
		return categoryNumber;
	}

	/**
	 * Indicates that floor selection should be hidden for this instance.
	 *
	 * @return "true" to hide the floor dropdown.
	 */
	@Override
	public String hideFloorSelection() {
		return "true";
	}

	/**
	 * Returns the page title for this instance.
	 *
	 * @return a formatted title string.
	 */
	@Override
	public String getTitle() {
		return "Spaces:Room Booking System - Architecture Library - Oklahoma State University";
	}

	/**
	 * Returns the page organization name for the UI.
	 *
	 * @return A string representing the organization name.
	 */
	@Override
	public String getOrganizationName() {
		return "Architecture Library - Oklahoma State University";
	}

	/**
	 * Returns the number of allowable time slots.
	 *
	 * @return "6" as a string.
	 */
	@Override
	public int getNumberofTimeSlots() {
		return 6;
	}

	/**
	 * Returns the help desk name.
	 *
	 * @return the string "Architecture Library Circulation Desk."
	 */
	@Override
	public String getHelpDeskName() {
		return "Architecture Library Circulation Desk.";
	}

	/**
	 * Returns the policy URL for room usage.
	 *
	 * @return the full URL to the policy page.
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
