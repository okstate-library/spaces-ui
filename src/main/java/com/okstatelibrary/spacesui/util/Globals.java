package com.okstatelibrary.spacesui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.okstatelibrary.spacesui.models.Room;

public class Globals {

//	private static Globals 
//
//	public static Globals getInstance() {
//		return globalsInstance;
//	}

	private Map<String, String> studyRooms;

	public Globals() {
	}

	public String getStudyRoomCategory(String roomNumber) {
		return this.studyRooms.get(roomNumber);
	}

	public String getStudyRoomByCategoryId(String categoryId) {
		return this.studyRooms.get(categoryId);
	}

	public void setStudyRooms(Map<String, String> studyRooms) {
		this.studyRooms = studyRooms;
	}

	/**
	 * @return the isProccessed
	 */
	public Boolean getIsProccessed() {
		return isProccessed;
	}

	/**
	 * @param isProccessed the isProccessed to set
	 */
	public void setIsProccessed(Boolean isProccessed) {
		this.isProccessed = isProccessed;
	}

	private Boolean isProccessed = false;

	/**
	 * Defines the floor drop down list
	 */
	private static final Map<String, String> categoryList = new HashMap<String, String>() {
		/**
		 * put predefine floor list
		 */
		private static final long serialVersionUID = 1L;

		{
			put("7030", "");
			put("7031", "vetmed");
			put("7032", "creativestudios");
		}
	};

	/**
	 * @return the categorylist
	 */
	public static Map<String, String> getCategorylist() {
		return categoryList;
	}

	public String getCategory(String categotyId) {
		return this.categoryList.get(categotyId);
	}

	private List<Room> roomList;

	public void setRoomDetails(List<Room> roomList) {
		this.roomList = roomList;
	}

	public String getRoomName(String roomId) {
		return this.roomList.stream().filter(customer -> roomId.equals(customer.getId())).findAny().orElse(null)
				.getName();
	}
}