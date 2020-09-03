package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.okstatelibrary.spacesui.util.StringUtil;

public class Category {

	private String cid;
	private String formid;
	private String items;

	@JsonCreator
	public Category(@JsonProperty("cid") String _cid, @JsonProperty("formid") String _formid,
			@JsonProperty("items") String[] _items) {
		this.setCid(_cid);
		this.setFormid(_formid);
		this.setItems(_items);
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = StringUtil.convertToCommaSeperatedString(items);
	}
}
