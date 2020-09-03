package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {

	private String id;
	private String name;
	private int capacity;
	private String description;
	private String formid;
	private String image;

	@JsonCreator
	public Room(@JsonProperty("id") String _id, @JsonProperty("name") String _name,
			@JsonProperty("capacity") int _capacity, @JsonProperty("description") String _description,
			@JsonProperty("formid") String _formid, @JsonProperty("image") String _image) {

		this.setId(_id);
		this.setName(_name);
		this.setCapacity(_capacity);
		this.setDescription(_description);
		this.setFormid(_formid);
		this.setImage(_image);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
