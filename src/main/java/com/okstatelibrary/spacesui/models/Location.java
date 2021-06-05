package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Location {

	private String lid;

	@JsonProperty("dates")
	private JsonNode dates;

	@JsonCreator
	public Location(@JsonProperty("lid") String _lid, @JsonProperty("dates") JsonNode _dates) {

		this.setLid(_lid);
		this.setDates(_dates);
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String l_id) {
		this.lid = l_id;
	}

	public JsonNode getDates() {
		return dates;
	}

	public void setDates(JsonNode _dates) {
		this.dates = _dates;
	}

}
