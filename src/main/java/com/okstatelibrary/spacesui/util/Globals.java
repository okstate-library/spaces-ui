package com.okstatelibrary.spacesui.util;

import java.util.List;
import java.util.Map;

import com.okstatelibrary.spacesui.models.Room;

/**
 * The {@code Globals} class acts as a shared in-memory storage for study room
 * metadata and processing status flags. It stores mappings between room numbers
 * and categories, as well as detailed room information for lookup by ID.
 *
 * <p>
 * This class is commonly used as a singleton-style data holder, often
 * initialized during application startup or during a refresh of system-wide
 * settings.
 */
public class Globals {

	/**
	 * A map of room numbers or category IDs to their respective study room category
	 * names.
	 */
	private Map<String, String> studyRooms;

	/** A list of all available room details used for lookup operations. */
	private List<Room> roomList;

	/**
	 * A flag indicating whether the study room data has been processed or
	 * initialized.
	 */
	private Boolean isProccessed = false;

	/** Default constructor. */
	public Globals() {
	}

	/**
	 * Returns the study room category name for a given room number.
	 *
	 * @param roomNumber the room number whose category is to be retrieved
	 * @return the study room category name, or {@code null} if not found
	 */
	public String getStudyRoomCategory(String roomNumber) {
		return this.studyRooms.get(roomNumber);
	}

	/**
	 * Returns the study room name or mapping based on a given category ID.
	 *
	 * @param categoryId the category ID used as a key in the map
	 * @return the mapped study room name, or {@code null} if not found
	 */
	public String getStudyRoomByCategoryId(String categoryId) {
		return this.studyRooms.get(categoryId);
	}

	/**
	 * Sets the map of study room data.
	 *
	 * @param studyRooms a map where keys are room numbers or category IDs, and
	 *                   values are category names or room labels
	 */
	public void setStudyRooms(Map<String, String> studyRooms) {
		this.studyRooms = studyRooms;
	}

	/**
	 * Returns whether the study room data has been processed.
	 *
	 * @return {@code true} if the data has been processed; {@code false} otherwise
	 */
	public Boolean getIsProccessed() {
		return isProccessed;
	}

	/**
	 * Sets the processed status of the study room data.
	 *
	 * @param isProccessed {@code true} to indicate the data has been processed
	 */
	public void setIsProccessed(Boolean isProccessed) {
		this.isProccessed = isProccessed;
	}

	/**
	 * Stores the list of study room details.
	 *
	 * @param roomList a list of {@link Room} objects representing room metadata
	 */
	public void setRoomDetails(List<Room> roomList) {
		this.roomList = roomList;
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
		return this.roomList.stream().filter(room -> roomId.equals(room.getId())).findAny().orElse(null).getName();
	}
}
