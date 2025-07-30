package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

public class edmonLowGlobalConfigs implements GlobalConfigs {

	/**
	 * Defines the floor drop down list
	 */
	private static final Map<String, String> floorList = new HashMap<String, String>() {
		/**
		 * put predefine floor list
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0", "Any");
			put("1", "First");
			put("2", "Second");
			put("3", "Third");
		}
	};

	/**
	 * Defines the Edmon low seat count drop down list
	 */
	private static final Map<String, String> edmonLowSeatList = new HashMap<String, String>() {
		/**
		 * Put defines data.
		 */
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
	 * Defines the edmon low seat count drop down list
	 */
	private static final Map<String, String> creativeStuiodSeatList = new HashMap<String, String>() {
		/**
		 * Put defines data.
		 */
		private static final long serialVersionUID = 1L;

		{
			put("1", "1+");
			put("3", "2+");

		}
	};

	private static final String edmonLowLibraryCategoryNumber = "7030";

	private static final String creativeStudioCategoryNumber = "7032";

	/**
	 * Defines the categories drop down list (categories)
	 */
	private static final Map<String, String> edmonLowCategoryList = new HashMap<String, String>() {
		/**
		 * put predefine floor list
		 */
		private static final long serialVersionUID = 1L;

		{
			put(edmonLowLibraryCategoryNumber, "Edmon Low Library");
			put(creativeStudioCategoryNumber, "Creative Studios");
		}
	};

	@Override
	public Map<String, String> getSeatList(String categoryNumber) {
		if (categoryNumber.equals(edmonLowLibraryCategoryNumber)) {
			return edmonLowSeatList;
		} else {
			return creativeStuiodSeatList;
		}
	}

	@Override
	public Map<String, String> getFloorList() {
		return floorList;
	}

	@Override
	public String getInstanceName() {

		return "edmon-low";
	}

	@Override
	public Map<String, String> getCategoryList() {
		return edmonLowCategoryList;
	}

	@Override
	public String getCategoryNumber() {
		return edmonLowLibraryCategoryNumber;
	}

	@Override
	public String hideFloorSelection(String categoryId) {

		if (categoryId.equals(edmonLowLibraryCategoryNumber)) {
			return "false";
		} else {
			return "true";
		}

	}

	@Override
	public String hideCategorySelection(String categoryId) {

		return "false";
	}

	@Override
	public boolean checkCategory(String categoryId) {
		return edmonLowCategoryList.containsKey(categoryId);
	}

}
