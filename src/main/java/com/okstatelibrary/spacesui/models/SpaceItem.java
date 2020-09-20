package com.okstatelibrary.spacesui.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.okstatelibrary.spacesui.util.StringUtil;

public class SpaceItem {
	private String id;
	private String name;
	private String description;
	private String roomDescription;
	private String image;
	private String capacity;
	private String formid;
	private String amenities;
	private Availability[] availability;

	public boolean visible;

	@JsonCreator
	public SpaceItem(@JsonProperty("id") String _id, @JsonProperty("name") String _name,
			@JsonProperty("description") String _description, @JsonProperty("image") String _image,
			@JsonProperty("capacity") String _capacity, @JsonProperty("formid") String _formid,
			@JsonProperty("availability") Availability[] _availability) {
		this.setId(_id);
		this.setName(_name);
		this.setDescription(_description);
		this.setRoomDescription(_description);
		this.setAmenities(_description);
		this.setImage(_image);
		this.setCapacity(_capacity);
		this.setFormid(_formid);
		this.setAvailability(_availability);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomDescription() {
		return roomDescription.replaceAll("\\<[^>]*>", "");
	}

	public String getAmenities() {
		return amenities;
	}

	public void setRoomDescription(String description) {

		String htmlRemoveStirng = description.replaceAll("\\<[^>]*>", "");

		int amenitiIndex = htmlRemoveStirng.indexOf("Available Amenities:");

		this.roomDescription = htmlRemoveStirng.substring(0, amenitiIndex);
	}

	public void setAmenities(String description) {

		String htmlRemoveStirng = description.replaceAll("\\<[^>]*>", "");

		String[] amenities = htmlRemoveStirng.split("\\r\\n\\r\\n");

		List<String> list = new ArrayList<String>();

		boolean isAdding = false;

		for (String amenity : amenities) {

			if (isAdding) {
				list.add(amenity.trim());
			}

			if (amenity.contains("Available Amenities:")) {
				isAdding = true;
			}

		}

		this.amenities = StringUtil.convertToPipeSeperatedString(list);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public Availability[] getAvailability() {
		return availability;
	}

	public void setAvailability(Availability[] availability) {
		this.availability = availability;
	}

	public void setVisibility(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getVisibility() {
		return this.visible;
	}

	public void emptyAvailability() {
		this.availability = null;
	}

	
}
