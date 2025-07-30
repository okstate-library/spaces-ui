package com.okstatelibrary.spacesui.globals;

import java.util.Map;

public interface GlobalConfigs {

	String getCategoryNumber();
	
	String getInstanceName();
	
	Map<String, String> getSeatList(String categoryId);
	
	Map<String, String> getFloorList();
	
	Map<String, String> getCategoryList();
	
	String hideFloorSelection(String categoryId);
	
	String hideCategorySelection(String categoryId);

	boolean checkCategory(String categoryId);
}
