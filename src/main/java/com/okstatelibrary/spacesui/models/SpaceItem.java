package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Space Item class
 * 
 * @author Damith
 *
 */
public class SpaceItem {

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String roomDescription;
	/**
	 * 
	 */
	private String image;
	/**
	 * 
	 */
	private String capacity;
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private String floor;
	/**
	 * 
	 */
	private String formid;
	/**
	 * 
	 */
	private String zoneId;
	/**
	 * 
	 */
	private Availability[] availability;

	/**
	 * 
	 */
	public boolean visible;

	/**
	 * Initialing the Space item class
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _image
	 * @param _capacity
	 * @param _formid
	 * @param _zoneId
	 * @param _availability
	 */
	@JsonCreator
	public SpaceItem(@JsonProperty("id") String _id, @JsonProperty("name") String _name,
			@JsonProperty("description") String _description, @JsonProperty("image") String _image,
			@JsonProperty("capacity") String _capacity, @JsonProperty("formid") String _formid,
			@JsonProperty("zoneId") String _zoneId, @JsonProperty("availability") Availability[] _availability) {
		this.setId(_id);
		this.setName(_name);
		this.setDescription(_description);
		this.setRoomDescription(_description);
		this.setImage(_image);
		this.setCapacity(_capacity);
		this.setFormid(_formid);
		this.setZoneId(_zoneId);
		this.setAvailability(_availability);
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name.trim();
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getRoomDescription() {
		return roomDescription.replaceAll("\\<[^>]*>", "");
	}

	/**
	 * @param description
	 */
	public void setRoomDescription(String description) {
		this.roomDescription = description;
	}

	/**
	 * @return
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return
	 */
	public String getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 */
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return
	 */
	public String getFormid() {
		return formid;
	}

	/**
	 * @param formid
	 */
	public void setFormid(String formid) {
		this.formid = formid;
	}

	/**
	 * @return
	 */
	public Availability[] getAvailability() {
		return availability;
	}

	/**
	 * @param availability
	 */
	public void setAvailability(Availability[] availability) {
		this.availability = availability;
	}

	/**
	 * @param visible
	 */
	public void setVisibility(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return
	 */
	public Boolean getVisibility() {
		return this.visible;
	}

	/**
	 * 
	 */
	public void emptyAvailability() {
		this.availability = null;
	}

	/**
	 * @return
	 */
	public String getFloor() {
		return Character.toString(this.zoneId.charAt(this.zoneId.length() - 1));
	}

	/**
	 * @param floor
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
