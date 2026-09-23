package com.okstatelibrary.spacesui.tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.okstatelibrary.spacesui.models.Category;
import com.okstatelibrary.spacesui.models.Room;

public class MasterData {

	private String categoryNumber;
	private Category[] categories;
	private List<Room> rooms;
	Map<String, String> studyRooms = new HashMap<>();

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	/**
	 * @return the categories
	 */
	public Category[] getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Category[] categories) {
		this.categories = categories;
	}



	public String getStudyRoomByCategoryId(String categoryId) {

		return this.studyRooms.get(categoryId);
	}

	/**
	 * @return the categoryNumber
	 */
	public String getCategoryNumber() {
		return categoryNumber;
	}

	/**
	 * @param categoryNumber the categoryNumber to set
	 */
	public void setCategoryNumber(String categoryNumber) {
		this.categoryNumber = categoryNumber;
	}

	public void setStudyRooms() {
		this.studyRooms.put(this.getCategoryNumber(), this.categories[0].getItems());

	}

	public String getStudyRooms() {
		return this.studyRooms.get(this.getCategoryNumber());

	}

	public void print() {

		System.out.println("category - " + this.getCategoryNumber());

		if (rooms != null) {
			
			System.out.println("Rooms size - " + this.rooms.size());
			
			for (Room room : rooms) {
								
				System.out.print(room.getId() + "/" + room.getName() + ",");
			}
		}

		System.out.println("getStudyRooms - " + this.getStudyRooms());

	}

	/**
	 * Retrieves the room name based on the provided room ID.
	 *
	 * @param roomId the unique identifier of the room
	 * @return the room name, or {@code null} if the ID is not found
	 * @throws NullPointerException if a room is not found (consider adding
	 *                              null-checking logic)
	 */
	public String getRoomName(String roomId) {
		return this.rooms.stream().filter(room -> roomId.equals(room.getId())).findAny().orElse(null).getName();
	}
}