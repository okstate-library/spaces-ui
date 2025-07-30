package com.okstatelibrary.spacesui.globals;

import java.util.HashMap;
import java.util.Map;

public class vetMetGlobalConfigs implements GlobalConfigs {

	/**
	 * Defines the edmon low seat count drop down list
	 */
	private static final Map<String, String> vetMedSeatList = new HashMap<String, String>() {
		/**
		 * Put defines data.
		 */
		private static final long serialVersionUID = 1L;

		{
			put("1", "1+");
			put("8", "4+");

		}
	};

	@Override
	public Map<String, String> getSeatList(String categoryId) {
		return vetMedSeatList;
	}

	@Override
	public Map<String, String> getFloorList() {
		return null;
	}

	@Override
	public String getInstanceName() {

		return "vet-med";
	}

	private static final String vetMedCategoryNumber = "7031";

	/**
	 * Defines the categories drop down list (categories)
	 */
	private static final Map<String, String> vetMedCategoryList = new HashMap<String, String>() {
		/**
		 * put predefine floor list
		 */
		private static final long serialVersionUID = 1L;

		{
			put(vetMedCategoryNumber, "Vet med");
		}
	};

	@Override
	public Map<String, String> getCategoryList() {
		return vetMedCategoryList;
	}

	@Override
	public String getCategoryNumber() {
		return vetMedCategoryNumber;
	}

	@Override
	public String hideFloorSelection(String categoryId) {
		return "true";
	}

	@Override
	public String hideCategorySelection(String categoryId) {
		return "true";
	}
	
	@Override
	public boolean checkCategory(String categoryId) {
		return vetMedCategoryList.containsKey(categoryId);
	}

}
